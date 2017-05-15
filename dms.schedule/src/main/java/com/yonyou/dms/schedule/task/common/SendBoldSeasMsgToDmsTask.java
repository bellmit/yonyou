package com.yonyou.dms.schedule.task.common;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.boldseas.ob.SendBoldMsgToDms;
import com.infoeai.eai.action.boldseas.ob.SendOwnerMsgTask;
import com.infoeai.eai.po.TtWarrantyRegistrationInterfaceHistoryPO;
import com.infoeai.eai.vo.OutBoundReturnVO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TtWarrantyRegistrationInterfaceInfoPO;
import com.yonyou.f4.common.database.DBService;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

/**
 * 发送车主信息给DMS
 * @author weixia
 * @date 2015-06-01
 */
@Component
public class SendBoldSeasMsgToDmsTask extends TenantSingletonTask {
	
	public Logger logger = Logger.getLogger(SendOwnerMsgTask.class);
	
	@Autowired 
	DBService dbService;
	
	@SuppressWarnings("unused")
	@Override
	public void execute() {
		String connectReturnMsg = "stateCode：1,stateInfo:成功";
		String connectReturnMsg1 = "stateCode：0,stateInfo:失败";
		try {
			logger.info("=================车主核实信息下发DMS开始===================");
//			dbService.beginTxn(getTenantId());
			
			List<TtWarrantyRegistrationInterfaceInfoPO> list = null;//boldseasDao.getData(1);
			if(null!= list && list.size()>0){
				for(TtWarrantyRegistrationInterfaceInfoPO po : list){
					TtWarrantyRegistrationInterfaceInfoPO updatePo = new TtWarrantyRegistrationInterfaceInfoPO();
					updatePo = TtWarrantyRegistrationInterfaceInfoPO.findFirst
							("DMS_OWSER_ID = ? AND DEALER_CODE = ? AND VIN = ? ", po.getString("DMS_OWSER_ID"),po.getString("DEALER_CODE"),po.getString("VIN"));
					updatePo.add(po);
					TtWarrantyRegistrationInterfaceHistoryPO historyPo = new TtWarrantyRegistrationInterfaceHistoryPO();
					try {
						String msgId = sendMsgToDms(po);
						if(Utility.testIsNotNull(msgId)){
							updatePo.setInteger("DCS_SEND_DMS_STATUS", 1); //发送成功
							historyPo.setString("CONNECT_RETURN_MSG", connectReturnMsg);
						}else{
							updatePo.setInteger("DCS_SEND_DMS_STATUS", 2); //发送失败
							historyPo.setString("CONNECT_RETURN_MSG", connectReturnMsg1);
						}
						updatePo.setString("MSG_ID", msgId);
						updatePo.setLong("UPDATE_BY", 10000000L);
						updatePo.setTimestamp("UPDATE_DATE", new Date());
						updatePo.saveIt();
					} catch (Exception e) {
						updatePo.setInteger("DCS_SEND_DMS_STATUS", 2); //发送失败
						historyPo.setString("CONNECT_RETURN_MSG", connectReturnMsg1);
						e.printStackTrace();
					}finally{
						/**
						 * 日志记录
						 */
						historyPo.setString("DMS_OWNER_ID", po.getString("DMS_OWNER_ID"));
						historyPo.setString("DEALER_CODE", po.getString("DEALER_CODE"));
						historyPo.setString("VIN", po.getString("VIN"));
						historyPo.setString("INTERFACE_MSG_TYPE", "3");  // 每日接口 1 重传 2 回传400 3 回传微信 4
						if(po.getInteger("DCS_SEND_DMS_STATUS")!=null && !po.getInteger("DCS_SEND_DMS_STATUS").equals("")){
							historyPo.setString("SEND_STATUS", po.getInteger("DCS_SEND_DMS_STATUS").toString());
						}else {
							historyPo.setString("SEND_STATUS", null);
						}
						historyPo.setString("BUILD_CONNECT_STATUS", "connection success");
						historyPo.setLong("CREATE_BY", 10000000L);
						historyPo.setTimestamp("CREATE_DATE", new Date());
						historyPo.insert();
					}
				}
			}
//			POContext.endTxn(true);
			logger.info("=================车主核实信息下发DMS结束===================");
		} catch (Exception e) {
			logger.info("=================车主核实信息下发DMS异常===================");
//			POContext.endTxn(false);
			e.printStackTrace();
		}
	}
	
	//发送消息到DMS
		public String sendMsgToDms(TtWarrantyRegistrationInterfaceInfoPO po){
			String msgId = "";
			try{
				OutBoundReturnVO returnVo = new OutBoundReturnVO();
				BeanUtils.copyProperties(returnVo, po); //将请求参数封装到OutBoundReturnVO
				logger.info("下发数据:"+returnVo.toString());
				msgId = SendBoldMsgToDms.sendData(returnVo);
			}catch(Exception t){
				msgId = "";
			}
			return msgId;
		}

}
