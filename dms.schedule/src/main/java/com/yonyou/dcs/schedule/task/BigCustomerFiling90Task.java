package com.yonyou.dcs.schedule.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dcs.gacfca.SADCS013Colud;
import com.yonyou.dms.common.Util.DateTimeUtil;
import com.yonyou.dms.common.Util.MailSender;
import com.yonyou.dms.common.domains.DTO.basedata.TtBigCustomerReportApprovalHisPO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerReportApprovalPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@TxnConn
@Component
public class BigCustomerFiling90Task extends TenantSingletonTask {
	private static final Logger logger = LoggerFactory.getLogger(BigCustomerFiling90Task.class);
	@Autowired
	SADCS013Colud SADCS013;

	@Override
	public void execute() throws Exception {
		logger.info("===============大客户报备状态修改开始===================");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());

		try {
			/****************************** 开启事物 *********************/
			List<TtBigCustomerReportApprovalPO> listPO = getBigCustomerRebateSendEmailUpdateList();
			Date date = new Date();
			if (null != listPO && listPO.size() > 0) {
				Set<String> wsNoSet = new HashSet<String>();
				for (int i = 0; i < listPO.size(); i++) {
					TtBigCustomerReportApprovalPO po = listPO.get(i);
					String dealerCode = (String) po.get("Dealer_Code");// 经销商代码
					Date reportApprovalDate = (Date) po.get("Report_Approval_Date");// 报备日期
					String wsNo = (String) po.get("Ws_No");// 报备单号
					String customerCompanyCode = (String) po.get("Customer_Company_Code");// 公司code
					Long reportApprovalUserId = (Long) po.get("Report_Approval_User_Id");
					// 发邮件
					boolean flag = true;// true 发送成功 发送异常
					List<TmDealerPO> listTmDealerPO = TmDealerPO.find("Dealer_Code=?", dealerCode);
					if (null != listTmDealerPO && listTmDealerPO.size() > 0) {
						String email = (String) listTmDealerPO.get(0).get("Email");
						MailSender bigCustomerMailSender = MailSender.INSTANCE;
						StringBuffer str = new StringBuffer(1000);
						str.append(" 尊的经销商合作伙伴，你们好！<br/> ");
						str.append(" <br/> ");
						str.append("    贵司" + DateTimeUtil.getNowYear(reportApprovalDate) + "年"
								+ DateTimeUtil.getNowMonth(reportApprovalDate) + "月"
								+ DateTimeUtil.getNowDay(reportApprovalDate)
								+ "日报备的大客户业务申请，截至今日仍未提交申请材料。按照CGCSL销售部发布的大客户政策规定：<br/> ");
						str.append(" <br/> ");
						str.append("    ① 经销商通过DMS系统提交集团销售申请表  <br/>");
						str.append(" <br/> ");
						str.append("    ② 区域大客户经理在线预审申请表和备案  <br/> ");
						str.append(" <br/> ");
						str.append("    ③ 经销商预审通过后3个月内提交申请材料（3个月的计算标准以所提交的新车零售发票中日期最早的为起始日期，逾期系统将自动屏蔽）<br/>");
						str.append(" <br/> ");
						str.append("    先通知贵司，在大客户报备申请通过后90天内，贵司仍未提交申请材料，已无法在DMS系统内继续申请该项大客户返利。   <br/>");
						str.append(" <br/> ");
						str.append("   以上，特此通知（此邮件不接收邮件，如有问题请联系lily.zhao@fcagroup.com）！ 如有任何不明，请联系大客户分管区域经理。<br/>");
						str.append(" <br/> ");
						str.append("    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						str.append(
								"    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;克莱斯勒（中国）汽车销售有限公司 销售部 大客户团队 <br/> ");
						str.append("    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						str.append(
								" 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
										+ DateTimeUtil.getNowYear(date) + "年" + DateTimeUtil.getNowMonth(date) + "月"
										+ DateTimeUtil.getNowDay(date) + "日");
						flag = bigCustomerMailSender.sendMail(email,
								"报备单号:" + wsNo + ",经销商代码：" + dealerCode + ",90天后未提交申请材料提醒", str.toString(), null);
					}
					logger.info("===============大客户报备发送邮件flag===================" + flag);
					if (!flag) {
						logger.info("===============大客户报备发送邮件异常===================");
						continue;
					} else {
						wsNoSet.add(wsNo + "," + dealerCode + "," + customerCompanyCode + "," + reportApprovalUserId);
					}
				}
				// 修改状态
				if (null != wsNoSet && wsNoSet.size() > 0) {
					TtBigCustomerReportApprovalPO newPO = null;
					String[] array = null;
					for (String ws : wsNoSet) {
						// 修改为审批拒绝
						array = ws.split(",");
						newPO = new TtBigCustomerReportApprovalPO();
						String wsNo = array[0];
						String dealerCode = array[1];
						String customerCompanyCode = array[2];
						String reportApprovalUserId = array[3];

						TtBigCustomerReportApprovalPO updatePO = TtBigCustomerReportApprovalPO
								.findFirst("ws_No=? and dealer_Code=?", wsNo, dealerCode);
						updatePO.setString("Send_Email", 2);
						updatePO.setInteger("Report_Approval_Status",
								OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_REPORT);
						updatePO.setString("Report_Approval_Remark", "90天系统自动报备转拒绝");
						updatePO.setTimestamp("Report_Approval_Date", format);
						updatePO.setLong("Update_By", 11111111L);
						updatePO.setTimestamp("Update_Date", format);
						updatePO.saveIt();

						/************************************** 写入大客户报备审批历史表 *************************************************/
						TtBigCustomerReportApprovalHisPO bigCustomerApprovalHisPo = new TtBigCustomerReportApprovalHisPO();
						// bigCustomerApprovalHisPo.setApprovalHisId(factory.getLongPK(bigCustomerApprovalHisPo));
						bigCustomerApprovalHisPo.setString("Customer_Company_Code", customerCompanyCode);
						bigCustomerApprovalHisPo.setString("Ws_No", wsNo);
						// 报备转拒绝
						bigCustomerApprovalHisPo.setInteger("Report_Approval_Status",
								OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_REPORT);
						bigCustomerApprovalHisPo.setString("Report_Approval_Remark", "90天系统自动报备转拒绝");
						bigCustomerApprovalHisPo.setTimestamp("Report_Approval_Date", format);
						bigCustomerApprovalHisPo.setLong("Report_Approval_UserId", new Long(reportApprovalUserId));
						bigCustomerApprovalHisPo.setInteger("Enable", OemDictCodeConstants.STATUS_ENABLE);
						bigCustomerApprovalHisPo.setLong("Create_By", 11111111L);
						bigCustomerApprovalHisPo.setTimestamp("Create_Date", format);
						bigCustomerApprovalHisPo.insert();
						logger.info("######################### 成功保存大客户报备审批信息 ######################");

						/************************************** 审批状态下发 *************************************************/
						SADCS013.execute(wsNo, dealerCode); // 调用下发接口
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("===============大客户报备状态修改异常===================");
		}
		logger.info("===============大客户报备状态修改结束===================");
	}

	private List<TtBigCustomerReportApprovalPO> getBigCustomerRebateSendEmailUpdateList() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  \n");
		sql.append("       TBCRA.WS_NO,  \n");
		sql.append("       TBCRA.DEALER_CODE, \n");
		sql.append("       TBCRA.CUSTOMER_COMPANY_CODE, \n");
		sql.append("       TBCRA.REPORT_APPROVAL_USER_ID, \n");
		sql.append("	   TBCRA.REPORT_APPROVAL_DATE \n");
		sql.append(" FROM TT_BIG_CUSTOMER_REPORT_APPROVAL TBCRA \n");
		sql.append("     LEFT JOIN TT_BIG_CUSTOMER_REBATE_APPROVAL TBRE \n");
		sql.append("     ON (TBCRA.WS_NO = TBRE.WS_NO AND TBCRA.DEALER_CODE=TBRE.DEALER_CODE) \n");
		sql.append("WHERE TBCRA.REPORT_APPROVAL_STATUS = " + OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_PASS
				+ "\n");
		sql.append("     AND ( TO_DAYS(now())-TO_DAYS(TBCRA.REPORT_APPROVAL_DATE)  ) >= 90 \n");
		sql.append("     AND TBRE.APPROVAL_ID IS NULL \n");
		sql.append("     AND TBCRA.SEND_EMAIL = 1 \n");
		System.out.println(sql.toString());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		List<TtBigCustomerReportApprovalPO> listPo = new ArrayList<>();
		for (Map map : list) {
			try {
				TtBigCustomerReportApprovalPO bean = new TtBigCustomerReportApprovalPO();
				bean.set("Ws_No", map.get("WS_NO"));
				bean.set("Dealer_Code", map.get("DEALER_CODE"));
				bean.set("Customer_Company_Code", map.get("CUSTOMER_COMPANY_CODE"));
				bean.set("Report_Approval_User_Id", map.get("REPORT_APPROVAL_USER_ID"));
				bean.set("Report_Approval_Date", map.get("REPORT_APPROVAL_DATE"));
				listPo.add(bean);
			} catch (Exception e) {
				System.err
						.println("List<TtBigCustomerReportApprovalPO> getBigCustomerRebateSendEmailUpdateList()查询PO失败");
				e.printStackTrace();
			}
		}

		return listPo;

	}

}
