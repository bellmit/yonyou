/**
 * @Description:接收BOLD消息并发送信息给DMS
 * @Copyright: Copyright (c) 2014
 * @Company: http://autosoft.ufida.com
 * @Date: 2014-2-28
 * @author lukezu
 * @version 1.0
 */
package com.infoeai.eai.action.boldseas.ob;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.ResultMsgVO;
import com.infoeai.eai.po.TtWarrantyRegistrationInterfaceHistoryPO;
import com.infoeai.eai.po.TtWxVehicleCustomerBindingPO;
import com.infoeai.eai.vo.OutBoundReturnVO;
import com.yonyou.dcs.util.DateConvert;
import com.yonyou.dms.common.Util.DateTimeUtil;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TtWarrantyRegistrationInterfaceInfoPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;


@Service
public class ReceiveBoldseasMsgImpl extends BaseService implements ReceiveBoldseasMsg{
	/**
     * <p>Field logger: logger</p>
     */
	private static final Logger logger = LoggerFactory.getLogger(ReceiveBoldseasMsgImpl.class);
	
    
	public ResultMsgVO receiveMsg(Map<String, String> paramsMap) throws Exception{
		logger.info("/************ 车主核实 接收数据 START ********/");
		beginDbService();
		
		ResultMsgVO msg = new ResultMsgVO();
		
		String dmsOwnerId = "";
		String dealerCode = "";
		String vin = "";
		
		Date startDate = new Date();
		String connectReturnMsg = "stateCode：1 stateInfo:成功";
		String connectReturnMsg1 = "stateCode：0 stateInfo:失败";
		String connectReturnMsg2 = "stateCode：0 stateInfo:失败[销售退回]";
		try {
			ConvertUtils.register(new DateConvert(),java.util.Date.class); //注册类型转换器
			TtWarrantyRegistrationInterfaceInfoPO infoParams = new TtWarrantyRegistrationInterfaceInfoPO();
			if(Utility.testIsNull(paramsMap.get("dealerCode"))){
				throw new NullPointerException("经销商代码不能为空！");
			}
			if(Utility.testIsNull(paramsMap.get("dmsOwnerId"))){
				throw new NullPointerException("客户编号不能为空！");
			}
			if(Utility.testIsNull(paramsMap.get("vin"))){
				throw new NullPointerException("vin不能为空！");
			}
			String obFlag = paramsMap.get("obFlag")==null?"0":paramsMap.get("obFlag");//obFlag
			if (!obFlag.equals("2")) { //车主核实
				if(Utility.testIsNull(paramsMap.get("outboundTime"))){
					throw new NullPointerException("outboundTime不能为空！");
				}
			}
			
			dmsOwnerId = CommonUtils.checkNull(paramsMap.get("dmsOwnerId"));
			dealerCode = CommonUtils.checkNull(paramsMap.get("dealerCode"));
			vin = CommonUtils.checkNull(paramsMap.get("vin"));
			//将请求参数封装到TtWarrantyRegistrationInterfaceInfoPO
			BeanUtils.populate(infoParams, paramsMap); 
			
			//将返回数据更新到DCS表中  
			
			List<TtWarrantyRegistrationInterfaceInfoPO> infoList = 
					TtWarrantyRegistrationInterfaceInfoPO.find(" DMS_OWNER_ID =? AND DEALER_CODE = ? AND VIN = ? ",dmsOwnerId,dealerCode,vin);
			//微信绑定数据有可能是DCS发出去的，也有可能是不存在DCS的车主信息，有可能更新与新增
			if(null != infoList && infoList.size() > 0){
				int sendStatus = 2;
				if (!obFlag.equals("2")) { 
					infoParams = infoList.get(0);
					logger.info("/************ 车主核实 表中存在BOLDSEAS发送数据，更新核实数据 START ********/");
					infoParams.setString("DATA_SOURCE","2"); //说明是boldseas回传数据(1:dms上报数据为1 2:boldseas 车主核实 3: 回传微信绑定)
					infoParams.setLong("UPDATE_BY",10000000L);
					infoParams.setTimestamp("UPDATE_DATE",new Date());
					infoParams.setInteger("DCS_SEND_DMS_STATUS",0);
					infoParams.setString("RECEIVE_BUILD_CONNECT_STATUS","connection success");
					infoParams.setTimestamp("RECEIVE_DATE",startDate);
					
					String isReturn = CommonUtils.checkNull(infoParams.get("IS_SALES_RETURN_STATUS"));
					
					if(isReturn.equals("1") && !isReturn.equals("")){
						infoParams.setString("RECEIVE_CONNECT_RETURN_MSG",connectReturnMsg2);
					}else{
						infoParams.setString("RECEIVE_CONNECT_RETURN_MSG",connectReturnMsg);
					}
					//根据VIN更新本地数据
					infoParams.saveIt();
					logger.info("/************ 车主核实 表中存在BOLDSEAS发送数据，更新核实数据 END ********/");

				}else{ //微信绑定率
					logger.info("/************ WX 表中存在BOLDSEAS发送数据，更新WX相关数据 START ********/");
					editWarrantyRegistrationInterfaceInfo(paramsMap,sendStatus,"");
					
					logger.info("/************ WX 在车主车辆资料表增加开始 ******************/ ");
					//在车主车辆资料表(TtWxVehicleCustomerBindingPO)中增加 (是否微信绑定与绑定日期)
					setWxVehicleCustomerBindingData(paramsMap, CommonUtils.checkNull(infoList.get(0).get("VIN"))); 
				}
				msg.setResultMsg(connectReturnMsg);
			}else{
				//如果是微信绑定，则往数据新增一条数据
				//String obFlag = paramsMap.get("obFlag");//obFlag
				if (null != obFlag && "2".equals(obFlag)) { //OB回传的结果
					logger.info("/************ WX 表中不存在BOLDSEAS发送数据，新增WX相关数据 START ********/");
					TtWarrantyRegistrationInterfaceInfoPO po = new TtWarrantyRegistrationInterfaceInfoPO();
					
					po.setString("DMS_OWNER_ID",dmsOwnerId);
					po.setString("DEALER_CODE",dealerCode);
					po.setString("VIN",vin);
					if (null != paramsMap.get("isBinding") && paramsMap.get("isBinding").length() != 0) {
						po.setInteger("IS_BINDING",new Integer(paramsMap.get("isBinding")));
					}
					po.setTimestamp("BINDING_DATE",DateTimeUtil.getStringFormat(paramsMap.get("bindingDate")));
					if (null != paramsMap.get("obFlag") && paramsMap.get("obFlag").length()!=0) {
						po.setInteger("OB_FLAG",Integer.parseInt(paramsMap.get("obFlag")));
					}
					po.setLong("CREATE_BY",10000000L);
					po.setTimestamp("CREATE_DATE",new Date());
					po.setString("RECEIVE_BUILD_CONNECT_STATUS","connection success");
					po.setString("RECEIVE_CONNECT_RETURN_MSG",connectReturnMsg);
					po.setTimestamp("RECEIVE_DATE",new Date());
					//po.setDataSource("3");
					po.saveIt();
					logger.info("/************ WX 表中不存在BOLDSEAS发送数据，新增WX相关数据 END ********/");
					
					
					logger.info("/************ WX 在车主车辆资料表增加 START **************/ ");
					//在车主车辆资料表(TtWxVehicleCustomerBindingPO)中增加 (是否微信绑定与绑定日期)
					setWxVehicleCustomerBindingData(paramsMap, paramsMap.get("vin")); 
					logger.info("/************ WX 在车主车辆资料表增加 END **************/ ");
					
					msg.setResultMsg(connectReturnMsg);
				} else {
					msg.setResultMsg(connectReturnMsg);
				}
			}
		}catch (NullPointerException e) {
			msg.setResultMsg(connectReturnMsg1);
			logger.info(e.toString());
		}catch (Exception e) {
			msg.setResultMsg(connectReturnMsg1);
			//异常对主表进行update
			StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE TT_WARRANTY_REGISTRATION_INTERFACE_INFO_DCS SET UPDATE_BY=10000000 , ");
			sql.append(" UPDATE_DATE=NOW(),RECEIVE_BUILD_CONNECT_STATUS='connection Error',RECEIVE_CONNECT_RETURN_MSG="+msg.getResultMsg()+",  ");
			sql.append(" RECEIVE_DATE=NOW()  ");
			sql.append(" WHERE DMS_OWNER_ID ='"+dmsOwnerId+"' AND DEALER_CODE = '"+dealerCode+"' AND VIN= '"+vin+"'  ");
			
			OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<Object>());
			logger.info(e.toString());
		}finally{
			logger.info("/************ WX 表中不存在BOLDSEAS发送数据，新增WX相关数据到历史表 START ********/");
			insertWarrantyRegistrationInterfaceHistory(paramsMap,msg.getResultMsg());
			dbService.endTxn(true);
			dbService.clean();
			logger.info("/************ WX 表中不存在BOLDSEAS发送数据，新增WX相关数据到历史表 END ********/");
			logger.info("/************ 车主核实 接收数据 end ********/");
		}
		return msg;
	}
	
	//发送消息到DMS
	public String sendMsgToDms(Map<String,String> paramsMap){
		String msgId = "";
		try{
			OutBoundReturnVO returnVo = new OutBoundReturnVO();
			BeanUtils.populate(returnVo, paramsMap); //将请求参数封装到TtWarrantyRegistrationInterfaceInfoPO
			logger.info("下发数据:"+returnVo.toString());
//			msgId = SendBoldMsgToDms.sendData(returnVo);
		}catch(Exception t){
			msgId = "";
		}
		return msgId;
	}
	
	//设置微信绑定表的数据
	public void setWxVehicleCustomerBindingData(Map<String,String> paramsMap, String vin) throws Exception{
		try{
			//在车主车辆资料表(TtWxVehicleCustomerBindingPO)中增加 (是否微信绑定与绑定日期)
			TtWxVehicleCustomerBindingPO po = new TtWxVehicleCustomerBindingPO();
			po.setString("DEALER_CODE",paramsMap.get("dealerCode"));//经销商code
			po.setString("DMS_OWNER_ID",paramsMap.get("dmsOwnerId"));//dmsOwnerid
			po.setString("VIN",paramsMap.get("vin"));
			
			List<TtWxVehicleCustomerBindingPO> isExist = TtWxVehicleCustomerBindingPO.find(
					" DEALER_CODE = ? AND DMS_OWNER_ID = ? AND VIN = ? ", 
					paramsMap.get("dealerCode"),paramsMap.get("dmsOwnerId"),paramsMap.get("vin"));
			if (isExist.size() == 0) {//为空，新增
				logger.info("=========在车主车辆资料表增加开始=============");
				po.setString("VIN",vin);//VIN
				if (null != paramsMap.get("isBinding")
						&& paramsMap.get("isBinding").length()!=0) {
					po.setInteger("IS_BINDING",Integer.parseInt(paramsMap.get("isBinding")));//是否绑定
				}
				po.setTimestamp("BINDING_DATE",DateTimeUtil.getStringFormat(paramsMap.get("bindingDate")));//绑定日期
				if (null != paramsMap.get("obFlag") 
						&& paramsMap.get("obFlag").length()!=0) {
					po.setInteger("OB_FLAG",Integer.parseInt(paramsMap.get("obFlag")));//obFlag
				}
				po.setLong("CREATE_BY",11111111L);//创建人
				po.setTimestamp("CREATE_DATE",new Date());//创建日期
				po.saveIt();//新增
				logger.info("=========在车主车辆资料表增加结束=============");
			} else {//修改
				logger.info("=========在车主车辆资料表修改开始=============");
				TtWxVehicleCustomerBindingPO updatePO = TtWxVehicleCustomerBindingPO.findFirst(
						" DEALER_CODE = ? AND DMS_OWNER_ID = ? AND VIN = ? ", 
					paramsMap.get("dealerCode"),paramsMap.get("dmsOwnerId"),paramsMap.get("vin"));
				updatePO.setString("VIN",vin);//VIN
				if (null != paramsMap.get("isBinding") 
						&& paramsMap.get("isBinding").length()!=0) {
					updatePO.setInteger("IS_BINDING",Integer.parseInt(paramsMap.get("isBinding")));//是否绑定
				}
				updatePO.setTimestamp("BINDING_DATE",DateTimeUtil.getStringFormat(paramsMap.get("bindingDate")));//绑定日期
				if (null != paramsMap.get("obFlag") 
						&& paramsMap.get("obFlag").length()!=0) {
					updatePO.setInteger("OB_FLAG",Integer.parseInt(paramsMap.get("obFlag")));//obFlag
				}
				updatePO.setLong("UPDATE_BY",11111111L);//修改人
				updatePO.setTimestamp("UPDATE_DATE",new Date());//修改日期
				updatePO.saveIt();
				logger.info("=========在车主车辆资料表修改结束=============");
			}
		}catch(Exception t){
			logger.info(t.getMessage());
			throw t;
		}
	}

	//WX 表中存在BOLDSEAS发送数据，更新WX相关数据 
	public void editWarrantyRegistrationInterfaceInfo(Map<String,String> paramsMap,int sendStatus,String msgId){
		StringBuffer sql = new StringBuffer();
		sql.append("  UPDATE TT_WARRANTY_REGISTRATION_INTERFACE_INFO  \n");
		sql.append("  SET \n");
		sql.append("    OB_DCS_SEND_DMS_STATUS = 0, \n");//WX下发到DMS状态
		sql.append("    UPDATE_DATE = ?, \n");//
		sql.append("    UPDATE_BY = ?, \n");//
		sql.append("    IS_BINDING = ?, \n");//是否认证
		sql.append("    BINDING_DATE = ?, \n");//绑定日期
		sql.append("    OB_FLAG = ?, \n");//OB结果
		sql.append("    OB_MSG_ID = ?, \n");//msg_id
		sql.append("    RECEIVE_BUILD_CONNECT_STATUS = 'connection success', \n");//连接状态
		sql.append("    RECEIVE_CONNECT_RETURN_MSG = '连接成功', \n");//连接返回消息
		sql.append("  	RECEIVE_DATE = sysdate  \n");
		//sql.append("  	DATA_SOURCE = '3'  \n");
		sql.append("  WHERE  \n");
		sql.append("    DMS_OWNER_ID = '"+paramsMap.get("dmsOwnerId")+"'  \n");
		sql.append("    AND DEALER_CODE = '"+paramsMap.get("dealerCode")+"' \n");
		sql.append("    AND VIN = '"+paramsMap.get("vin")+"' \n");
		List<Object> params = new ArrayList<Object>();
//		params.add(sendStatus);
		params.add(new Date());
		params.add(10000000L);
		params.add(paramsMap.get("isBinding"));
		params.add(paramsMap.get("bindingDate"));
		params.add(paramsMap.get("obFlag"));
		params.add(msgId);
		OemDAOUtil.execBatchPreparement(sql.toString(), params);
	}
	

	//WX 表中不存在BOLDSEAS发送数据，新增WX相关数据到历史表
	public void insertWarrantyRegistrationInterfaceHistory(Map<String,String> paramsMap,String connectReturnMsg){
		TtWarrantyRegistrationInterfaceHistoryPO historyPo = new TtWarrantyRegistrationInterfaceHistoryPO();
//		historyPo.setId(new Long(SequenceManager.getSequence(null)));
		historyPo.setString("DMS_OWNER_ID",paramsMap.get("dmsOwnerId"));
		historyPo.setString("DEALER_CODE",paramsMap.get("dealerCode"));
		historyPo.setString("VIN",paramsMap.get("vin"));
		historyPo.setString("INTERFACE_MSG_TYPE","5");//回传调用
		historyPo.setString("SEND_STATUS","1");
		historyPo.setString("BUILD_CONNECT_STATUS","connection success");
		historyPo.setString("CONNECT_RETURN_MSG",connectReturnMsg);
		historyPo.setLong("CREATE_BY",10000000L);
		historyPo.setTimestamp("CREATE_DATE",new Date());
		historyPo.saveIt();
	}
	
}
