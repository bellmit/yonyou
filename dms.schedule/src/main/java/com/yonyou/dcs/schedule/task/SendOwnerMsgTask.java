package com.yonyou.dcs.schedule.task;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.boldseas.ob.ReceiveBoldseasMsg;
import com.infoeai.eai.action.boldseas.ob.SendBoldSeasMsg;
import com.infoeai.eai.po.TtWarrantyRegistrationInterfaceHistoryPO;
import com.infoeai.eai.vo.ReceiveValidateFailureVO;
import com.infoeai.eai.vo.SendBoldVO;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.common.domains.PO.basedata.TtWarrantyRegistrationInterfaceInfoPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;


/**
 * 车辆审核定时任务-每日
 * @author weixia
 * 
 */
@SuppressWarnings("rawtypes")
@TxnConn
@Component
public class SendOwnerMsgTask extends TenantSingletonTask{
	
	private static final Logger logger = LoggerFactory.getLogger(SendOwnerMsgTask.class);
	/**
     * <p>Field factory: 数据工厂对象</p>
     */
	
	@Autowired
	ReceiveBoldseasMsg dao;
	
	public void execute() {
		try {
			logger.info("********************   车辆信息核实发送开始    ************************");
			
			Calendar cal = Calendar.getInstance();
			SendBoldVO infoKey = null;
			TtWarrantyRegistrationInterfaceHistoryPO historyPo ;
			//获得前一天可发送的数据
			List<SendBoldVO>  list = getTtWarrantyRegistrationInterfaceInfoList();
			for(int i = 0;i<list.size();i++){
				SendBoldVO sendMsgVo = list.get(i); 
				infoKey = list.get(i);
				TtWarrantyRegistrationInterfaceInfoPO infoPo = 
						TtWarrantyRegistrationInterfaceInfoPO.findFirst(" DMS_OWNER_ID = ? AND  DEALER_CODE = ? AND VIN = ? "
								, infoKey.getDmsOwnerId(),infoKey.getDealerCode(),infoKey.getVin());
				try {
					//发送数据给BOlDSEAS（ValidatorSuccess 0.接口调用失败 1.校验成功 2.校验失败 3.校验信息写入本地数据库错误）
					logger.info("********************准备发送数据************************");
					List<ReceiveValidateFailureVO> validateList = new SendBoldSeasMsg().sendMsgToBold(sendMsgVo);
					if(null != validateList && validateList.size() > 0 ){
						ReceiveValidateFailureVO failureVO = validateList.get(0);
						String returnMsg = failureVO.toString();
						infoPo.setString("CONNECT_RETURN_MSG",returnMsg);//连接返回消息
						if(failureVO.getStateCode().equals("001")){ //校验成功只会返回一条数据并且是001
							infoPo.setInteger("VALIDATOR_SUCCESS",1); //校验成功
						}else{
							infoPo.setInteger("VALIDATOR_SUCCESS",2); //校验失败
						}
						if(failureVO.getStateCode().equals("000") ||failureVO.getStateCode().equals("003") || failureVO.getStateCode().equals("1033")){
							infoPo.setInteger("DCS_SEND_BOLD_STATUS",2); //0.未发送 1.发送成功 2.发送失败
						}else {
							infoPo.setInteger("DCS_SEND_BOLD_STATUS",1); //0.未发送 1.发送成功 2.发送失败
						}
						infoPo.setString("BUILD_CONNECT_STATUS","connection success");
					}
					logger.info("********************成功发送数据************************");
				} catch (ConnectException e) {
					logger.info("连接超时");
					infoPo.setInteger("DCS_SEND_BOLD_STATUS",2); //1.发送成功 2.发送失败
					infoPo.setInteger("VALIDATOR_SUCCESS",0); //接口调用失败
					infoPo.setString("BUILD_CONNECT_STATUS","connection time out");
					infoPo.setString("CONNECT_RETURN_MSG","连接超时");
					logger.info(e.getMessage(),e);
				}catch (SocketTimeoutException e1) {
					logger.info("read time out");
					infoPo.setInteger("DCS_SEND_BOLD_STATUS",2); //1.发送成功 2.发送失败
					infoPo.setInteger("VALIDATOR_SUCCESS",0); //接口调用失败
					infoPo.setString("BUILD_CONNECT_STATUS","read time out");
					infoPo.setString("CONNECT_RETURN_MSG","read time out");
					logger.info(e1.getMessage(),e1);
				}catch (Exception e2) {
					logger.info("异常");
					infoPo.setInteger("DCS_SEND_BOLD_STATUS",2); //1.发送成功 2.发送失败
					infoPo.setInteger("VALIDATOR_SUCCESS",0); //接口调用失败
					infoPo.setString("BUILD_CONNECT_STATUS",e2.getMessage());
					infoPo.setString("CONNECT_RETURN_MSG",e2.getMessage());
					logger.info(e2.getMessage(),e2);
				}
				infoPo.setString("SEND_NUM","0");
				infoPo.setTimestamp("SEND_DATE",new Date());
				infoPo.saveIt();
				
				historyPo = new TtWarrantyRegistrationInterfaceHistoryPO();
				historyPo.setString("DMS_OWNER_ID",infoKey.getDmsOwnerId());
				historyPo.setString("DEALER_CODE",infoKey.getDealerCode());
				historyPo.setString("VIN",infoKey.getVin());
				historyPo.setString("INTERFACE_MSG_TYPE","1"); // 每日接口 1 重传 2 回传400 3 回传微信 4
				historyPo.setString("SEND_STATUS",infoPo.getString("DCS_SEND_BOLD_STATUS"));
				historyPo.setString("BUILD_CONNECT_STATUS",infoPo.getString("BUILD_CONNECT_STATUS"));
				historyPo.setString("CONNECT_RETURN_MSG",infoPo.getString("CONNECT_RETURN_MSG"));
				historyPo.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
				historyPo.setTimestamp("CREATE_DATE",cal.getTime());
				historyPo.saveIt();
//				tempList.add(historyPo);
			}
//			if(null != tempList && tempList.size() > 0){
//				factory.insert(tempList); //save保修登记信息
//				logger.info("*************************** 保存保修登记历史数据******************************");
//			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("********************   车辆信息核实发送结束   ************************");
	} 
	
	/**
	 * 查询当前未发送数据
	 * @return
	 * @throws Exception
	 */
	
	private List<SendBoldVO> getTtWarrantyRegistrationInterfaceInfoList(){
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT T.INTERFACE_ID,T.CLIENT_TYPE,T.NAME,T.GENDER,T.CELLPHONE,T.ID_OR_COMP_CODE,T.PROVINCE_ID,T.CITY_ID,T.DISTRICT,T.ADDRESS,T.EMAIL,T.POST_CODE,T.DMS_OWNER_ID," +
				"T.DEALER_CODE,T.VIN,T.BRAND_ID,T.MODE_ID,T.STYLE_ID,T.COLOR_ID,T.IS_SECOND_TIME,DATE_FORMAT(T.BUY_TIME,'%Y-%M-%D') BUY_TIME,T.IS_OVER_DUE,T.IS_SALES_RETURN_STATUS," +
				"T.COMPANY_NAME,T.VEHICLE_PURPOSE,T.ENGINE_NO,T.ORDER_NO  " +
				"FROM TT_WARRANTY_REGISTRATION_INTERFACE_INFO_DCS T ,TM_VEHICLE_DEC ve ,("+OemBaseDAO.getVwMaterialSql()+") vm\n");
		sql.append("  WHERE 1=1  \n");
		sql.append("  AND T.DCS_SEND_BOLD_STATUS = 0  \n");
		sql.append("  AND T.SEND_NUM = 0 \n");
		sql.append("  AND T.VIN=ve.VIN AND ve.MATERIAL_ID=vm.MATERIAL_ID  \n");
		sql.append("  AND (not exists (SELECT tc.BRAND_CODE FROM TI_BRAND_CODE_DCS tc WHERE tc.IS_DEL ='0' and tc.BRAND_CODE=vm.BRAND_CODE ))   \n ");//过滤FIAT品牌
//		sql.append("  AND DATE(SYSDATE) - DATE( T.BUY_TIME) = 1 \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		List<SendBoldVO> resultList = new ArrayList<>();
		for (Map map : list) {
			SendBoldVO bean = new SendBoldVO();
			bean.setDmsId(CommonUtils.checkNull(map.get("INTERFACE_ID")));
			bean.setClientType(CommonUtils.checkNull(map.get("CLIENT_TYPE")));
			bean.setName(CommonUtils.checkNull(map.get("NAME")));
			bean.setGender(CommonUtils.checkNull(map.get("GENDER")));
			bean.setCellphone(CommonUtils.checkNull(map.get("CELLPHONE")));
			bean.setIdOrCompCode(CommonUtils.checkNull(map.get("ID_OR_COMP_CODE")));
			bean.setProvinceId(CommonUtils.checkNull(map.get("PROVINCE_ID")));
			bean.setCityId(CommonUtils.checkNull(map.get("CITY_ID")));
			bean.setDistrict(CommonUtils.checkNull(map.get("DISTRICT")));
			bean.setAddress(CommonUtils.checkNull(map.get("ADDRESS")));
			bean.setEmail(CommonUtils.checkNull(map.get("EMAIL")));
			bean.setPostCode(CommonUtils.checkNull(map.get("POST_CODE")));
			bean.setDmsOwnerId(CommonUtils.checkNull(map.get("DMS_OWNER_ID")));
			bean.setDealerCode(CommonUtils.checkNull(map.get("DEALER_CODE")));
			bean.setVin(CommonUtils.checkNull(map.get("VIN")));
			bean.setBrandId(CommonUtils.checkNull(map.get("BRAND_ID")));
			bean.setModeId(CommonUtils.checkNull(map.get("MODE_ID")));
			bean.setStyleId(CommonUtils.checkNull(map.get("STYLE_ID")));
			bean.setColorId(CommonUtils.checkNull(map.get("COLOR_ID")));
			bean.setIsSecondTime(CommonUtils.checkNull(map.get("IS_SECOND_TIME")));
			bean.setBuyTime(CommonUtils.checkNull(map.get("BUY_TIME")));
			bean.setIsOverDue(CommonUtils.checkNull(map.get("IS_OVER_DUE")));
			bean.setIsReturn(CommonUtils.checkNull(map.get("IS_SALES_RETURN_STATUS")));
			bean.setCompanyName(CommonUtils.checkNull(map.get("COMPANY_NAME")));
			bean.setVehiclePurpose(CommonUtils.checkNull(map.get("VEHICLE_PURPOSE")));
			bean.setEngineNo(CommonUtils.checkNull(map.get("ENGINE_NO")));
			bean.setOrderNo(CommonUtils.checkNull(map.get("ORDER_NO")));
			resultList.add(bean);
		}
		return resultList;
	}
	
	
	
	
	public static void main(String[] args) {
		SendOwnerMsgTask task = new SendOwnerMsgTask();
		task.execute();
	}

}
