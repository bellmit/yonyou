package com.yonyou.dcs.schedule.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dcs.gacfca.SADCS015Cloud;
import com.yonyou.dcs.schedule.dao.BigCustomerSystemRusDao;
import com.yonyou.dms.common.domains.PO.basedata.TmHolidayPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerRebateApprovalHisPO;
import com.yonyou.dms.common.domains.PO.customer.TtBigCustomerRebateApprovalPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@TxnConn
@Component
public class BigCustomerSystemTask extends TenantSingletonTask {
	private static final Logger logger = LoggerFactory.getLogger(BigCustomerSystemTask.class);
	/**
	 * 大客户申请驳回逾期未提交（判断是否是15个自然工作日）定时任务
	 */
	@Autowired
	BigCustomerSystemRusDao bigCustomerSystemRusDao;
	@Autowired
	SADCS015Cloud SADCS015;

	@Override
	public void execute() throws Exception {
		logger.info("############################### 大客户申请驳回逾期未提交 TASK EXEC START ##########################");
		try {
			List<Map> list = bigCustomerSystemRusDao.systemAutoRus();
			String wsNo = "";
			String dealerCode = "";
			String customerCode = "";
			String startDate = "";
			String userId = "";
			int day = 0;
			// 查询当前年的节假日
			TmHolidayPO thPO = new TmHolidayPO();
			Calendar c = Calendar.getInstance();

			LazyList<TmHolidayPO> list2 = TmHolidayPO.find("HolidayYear=? and Status=?", c.get(Calendar.YEAR),
					OemDictCodeConstants.STATUS_ENABLE);

			List<TtBigCustomerRebateApprovalHisPO> hisList = new ArrayList<TtBigCustomerRebateApprovalHisPO>();
			for (Map<String, Object> map : list) {
				startDate = (String) map.get("APPROVAL_DATE");// 开始日期
				day = bigCustomerSystemRusDao.isWorkDay(list2, startDate);
				if (day > 15) {
					Calendar cal = Calendar.getInstance();// 获取日期
					wsNo = (String) map.get("WS_NO");
					dealerCode = (String) map.get("DEALER_CODE");
					customerCode = (String) map.get("BIG_CUSTOMER_CODE");
					userId = String.valueOf(map.get("REBATE_APPROVAL_USER_ID"));

					TtBigCustomerRebateApprovalPO updateBigCustomerPo = TtBigCustomerRebateApprovalPO
							.findFirst("Dealer_Code=? and Ws_No=?", dealerCode, wsNo);
					updateBigCustomerPo.setInteger("Rebate_Approval_Status",
							OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_SYSTEM_RUS);
					updateBigCustomerPo.setInteger("Rebate_Approval_Remark",
							OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_SYSTEM_RUS_REMARK);
					updateBigCustomerPo.setTimestamp("Rebate_Approval_Date", cal.getTime());
					updateBigCustomerPo.setTimestamp("Activity_Date", cal.getTime());
					// updateBigCustomerPo.setRebateApprovalUserId(Long.parseLong(Constant.SYS_USER.toString()));
					updateBigCustomerPo.setString("Update_By", Long.parseLong(OemDictCodeConstants.FLASH_UID));
					updateBigCustomerPo.setTimestamp("Update_Date", cal.getTime());
					updateBigCustomerPo.saveIt();
					logger.info("############ 更新单号：" + wsNo + ",经销商代码" + dealerCode + " 为系统转拒绝状态 #############");

					// 下发审批数据
					SADCS015.execute(wsNo, dealerCode);

					/************************************** 写入大客户返利审批历史表 *************************************************/
					TtBigCustomerRebateApprovalHisPO bigCustomerApprovalHisPo = new TtBigCustomerRebateApprovalHisPO();
					// bigCustomerApprovalHisPo.setApprovalHisId(factory.getLongPK(bigCustomerApprovalHisPo));
					bigCustomerApprovalHisPo.setString("Big_Customer_Code", customerCode);
					bigCustomerApprovalHisPo.setString("Ws_No", wsNo);
					bigCustomerApprovalHisPo.setTimestamp("Activity_Date", cal.getTime());
					bigCustomerApprovalHisPo.setInteger("Rebate_Approval_Status",
							OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_SYSTEM_RUS);
					bigCustomerApprovalHisPo.setInteger("Rebate_Approval_Remark",
							OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_SYSTEM_RUS_REMARK);
					bigCustomerApprovalHisPo.setTimestamp("Rebate_Approval_Date", cal.getTime());
					bigCustomerApprovalHisPo.setLong("Rebate_Approval_UserId",
							null != userId ? Long.parseLong(userId) : null);
					bigCustomerApprovalHisPo.setInteger("Enable", OemDictCodeConstants.STATUS_ENABLE);
					bigCustomerApprovalHisPo.setLong("Create_By", OemDictCodeConstants.SYS_USERID_GLO_INTERFACE);
					bigCustomerApprovalHisPo.setTimestamp("Create_Date", cal.getTime());
					bigCustomerApprovalHisPo.insert();
					// hisList.add(bigCustomerApprovalHisPo);
					logger.info("############ 写入大客户返利审批历史表 #############");
				}
			}
			// if (null != hisList && hisList.size() > 0) {
			//
			// factory.insert(hisList);
			// logger.info("############ 写入大客户返利审批历史表 #############");
			// }
		} catch (Exception e) {
			logger.info("######## 大客戶逾期未提交，系统转拒绝 自动处理异常 ##########");
			logger.error(e.getMessage(), e);
		} finally {
		}
		logger.info("############################### 大客户申请驳回逾期未提交 TASK EXEC  ##########################");
	}

}
