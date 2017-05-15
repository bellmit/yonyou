package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yonyou.dcs.dao.SendDmsMsgToBoldCloudDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.WarrantyRegistDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtWarrantyRegistrationInterfaceInfoPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domains.DTO.baseData.VehicleInfoDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 
 * Title:SendDmsMsgToBoldCloudImpl
 * Description: 车主核实信息上报
 * @author DC
 * @date 2017年4月12日 下午6:04:41
 * result msg 1：成功 0：失败
 */
@Service
public class SendDmsMsgToBoldCloudImpl extends BaseCloudImpl implements SendDmsMsgToBoldCloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SendDmsMsgToBoldCloudImpl.class);
	
	/**
     * <p>Field vehicleInfoList: 车的品牌相关信息</p>
     */
	private static List<Map<String, VehicleInfoDTO>> vehicleInfoList = null;
	
	@Autowired
	SendDmsMsgToBoldCloudDao dao;

	@Override
	public String handleExecutor(List<WarrantyRegistDTO> dtoList) throws Exception {
		String msg = "1";
		logger.info("*************************** 开始处理保修登记数据 ******************************");
		//品牌，车系，车型集合
		vehicleInfoList = dao.getVehicle(); 
		for (WarrantyRegistDTO entry : dtoList) {
			try {
				dealUpData(entry,vehicleInfoList);
			} catch (Exception e) {
				logger.error("处理保修登记数据失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("*************************** 成功处理保修登记数据 ******************************");
		return msg;
	}

	/** 
	 * 处理上报数据 - 业务操作流程:
	 * a.获取VO数据，转换品牌，车系，车型treeCode 
	 * b.http post发送VO数据到boldseas 
	 * c.保存DMS上报的OB数据 
	 * d.保存历史数据
	 * @param vehicleInfoList 
	 */
	@SuppressWarnings("unchecked")
	private void dealUpData(WarrantyRegistDTO entry, List<Map<String, VehicleInfoDTO>> vehicleInfoList) {
		Calendar cal = Calendar.getInstance();
		//定义保存保修登记信息的集合
		List<TtWarrantyRegistrationInterfaceInfoPO> saveList = new ArrayList<TtWarrantyRegistrationInterfaceInfoPO>();
		//定义获取品牌，车系，车型数据VO
		VehicleInfoDTO infoVo = new VehicleInfoDTO();
		//注册类型转换器
		//BeanUtils.register(new DateConvert(),java.util.Date.class); 
		logger.info("*************************** 开始接收保修登记数据 ******************************");
		TtWarrantyRegistrationInterfaceInfoPO infoPo = new TtWarrantyRegistrationInterfaceInfoPO();
		Map<String, Object> map = new HashMap<>();
		String dealerCode = "";
		//获取BEAN 数据
		if(StringUtils.isNullOrEmpty(entry.getVin())){
			throw new NullPointerException("DE dat数据文件中的vin是空值!");
		}
		if(StringUtils.isNullOrEmpty(entry.getDmsOwnerId())){
			throw new NullPointerException("dmsOwnerId是空值!");
		}
		if(StringUtils.isNullOrEmpty(entry.getDealerCode())){
			throw new NullPointerException("dealerCode是空值!");
		}
		//获取经销商信息
		map = dao.getSaDcsDealerCode(entry.getDealerCode());
		dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
		entry.setDealerCode(dealerCode);
		//获取品牌，车系，车型对应的ID(彪杨接口需要存ID，不是下端传的CODE)
		infoVo = validatorPoCode(entry.getBrandId()+"@"+entry.getModeId()+"@"+entry.getStyleId());
		if(null != infoVo){
			entry.setBrandId(infoVo.getBrandId());
			entry.setModeId(infoVo.getModelId());
			entry.setStyleId(infoVo.getStyleId());
			entry.setColorId(entry.getColorId());
		}
		
		//写入数据到DCS TT_WARRANTY_REGISTRATION_INTERFACE_INFO 表
		//将DMS数据赋值给INFOPO
		BeanUtils.copyProperties(infoPo, entry);
		infoPo.setString("DEALER_CODE", dealerCode);
		infoPo.setInteger("DCS_SEND_BOLD_STATUS", 0);//记录为未发送的数据
		infoPo.setString("SEND_NUM", "0");
		infoPo.setString("IS_SEND_EMAIL", "0");
		logger.info("***************************** 准备查询数据是否存在 ************************** ");
		//根据VIN判断是不是同一个保修
		//定义获取品牌，车系，车型数据LIST
		List<TtWarrantyRegistrationInterfaceInfoPO> dataList = TtWarrantyRegistrationInterfaceInfoPO.find
				(" DMS_OWNER_ID = ? AND DEALER_CODE = ? AND VIN = ? ", infoPo.getString("DMS_OWNER_ID"),dealerCode,infoPo.getString("VIN"));
		logger.info("***************************** 完成查询数据是否存在 ************************** ");
		
		//如果是重新上报保修登记，根据VIN更新本地数据
		if(null != dataList && dataList.size() > 0){
			logger.info("/*************************** 更新保修登记信息 *********************************/");
			String dmsOwnerId = infoPo.getString("DMS_OWNER_ID");
			Integer dcsSendBoldStatus = infoPo.getInteger("DCS_SEND_BOLD_STATUS");//发送状态
			if(entry.getIsSalesReturnStatus().equals("1")){
				//更新销售退回信息
				updateIsSalesReturn(entry);
			}else{
				updateWarrantyRegistrationInterfaceInfo(entry,dealerCode,dmsOwnerId,dcsSendBoldStatus);
			}
			logger.info("/*************************** 完成更新保修登记信息 *********************************/");
		}else{
			logger.info("/*************************** 保修登记信息集合 *********************************/");
			infoPo.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
			infoPo.setTimestamp("CREATE_DATE", cal.getTime());
			saveList.add(infoPo);
		}
		if(null != saveList && saveList.size() > 0){
			for(int i=0;i<saveList.size();i++){
				saveList.get(i).insert(); //save保修登记信息
			}
			logger.info("*************************** 保存保修登记数据******************************");
		}
		
	}

	/**
	 * 品牌车系车型CODE转换成彪杨对应的ID
	 */
	private static VehicleInfoDTO validatorPoCode(String code){
		VehicleInfoDTO infoVo = null;
		for(Map<String,VehicleInfoDTO> map : vehicleInfoList){
			infoVo =  map.get(code);
			if(null != infoVo){
				break;
			}
		}
		return infoVo;
	}
	
	/**
	 * 更新销售退回信息
	 * @param sendMsgVo
	 * @return
	 */
	public void updateIsSalesReturn(WarrantyRegistDTO sendMsgVo){
		StringBuffer sql = new StringBuffer();
		sql.append("  UPDATE TT_WARRANTY_REGISTRATION_INTERFACE_INFO  \n");
		sql.append("  SET \n");
		sql.append("    IS_SECOND_TIME = ?, \n");
		sql.append("    BUY_TIME = ?, \n");
		sql.append("    DCS_SEND_BOLD_STATUS = 0, \n");
		sql.append("    IS_SALES_RETURN_STATUS = ?, \n");
		sql.append("    UPDATE_BY = "+DEConstant.DE_UPDATE_BY+", \n");
		sql.append("    UPDATE_DATE = sysdate, \n");
		sql.append("    SEND_NUM = '0', \n");
		sql.append("    SEND_DATE = null, \n");
		sql.append("    IS_SEND_EMAIL = '0' \n");
		sql.append("  WHERE  \n");
		sql.append("    DMS_OWNER_ID = '"+sendMsgVo.getDmsOwnerId()+"'  \n");
		sql.append("    AND DEALER_CODE = '"+sendMsgVo.getDealerCode()+"' \n");
		sql.append("    AND VIN = '"+sendMsgVo.getVin()+"' \n");
		List<Object> params = new ArrayList<Object>();
		params.add(sendMsgVo.getIsSecondTime());
		params.add(sendMsgVo.getBuyTime());
		params.add(sendMsgVo.getIsSalesReturnStatus());
		OemDAOUtil.execBatchPreparement(sql.toString(), params);
	}
	
	/**
	 * 更新OB车主信息
	 * @param sendMsgVo
	 * @param dealerCode
	 * @param dmsOwnerId
	 * @param validatorSuccess
	 * @param dcsSendBoldStatus
	 * @param buildConnectStatus
	 * @param connectReturnMsg
	 * @return
	 */
	public void updateWarrantyRegistrationInterfaceInfo(WarrantyRegistDTO sendMsgVo,String dealerCode,String dmsOwnerId,
			int dcsSendBoldStatus){
		StringBuffer sql = new StringBuffer();
		sql.append("  UPDATE TT_WARRANTY_REGISTRATION_INTERFACE_INFO  \n");
		sql.append("  SET \n");
		sql.append("    CLIENT_TYPE = ?, \n");
		sql.append("    NAME = ?, \n");
		sql.append("    GENDER = ?, \n");
		sql.append("    CELLPHONE = ?, \n");
		sql.append("    ID_OR_COMP_CODE = ?, \n");
		sql.append("    PROVINCE_ID = ?, \n");
		sql.append("    CITY_ID = ?, \n");
		sql.append("    DISTRICT = ?, \n");
		sql.append("    ADDRESS = ?, \n");
		sql.append("    EMAIL = ?, \n");
		sql.append("    POST_CODE = ?, \n");
		sql.append("    DMS_OWNER_ID = ?, \n");
		sql.append("    DEALER_CODE = ?, \n");
		sql.append("    VIN = ?, \n");
		sql.append("    BRAND_ID = ?, \n");
		sql.append("    MODE_ID = ?, \n");
		sql.append("    STYLE_ID = ?, \n");
		sql.append("    COLOR_ID = ?, \n");
		sql.append("    IS_VERIFY_ADDRESS = null, \n");
		sql.append("    IS_OUTBOUND = null, \n");
		sql.append("    OB_IS_SUCCESS = null, \n");
		sql.append("    REASONS = null, \n");
		sql.append("    IS_UPDATE = null, \n");
		sql.append("    OUTBOUND_REMARK = null, \n");
		sql.append("    IS_OWNER = null, \n");
		sql.append("    OUTBOUND_TIME = null, \n");
		sql.append("    IS_BINDING = null, \n");
		sql.append("    IS_SECOND_TIME = ?, \n");
		sql.append("    DCS_SEND_BOLD_STATUS = ?, \n");
		sql.append("    DCS_SEND_DMS_STATUS = null, \n");
		sql.append("    BUY_TIME = ?, \n");
		sql.append("    UPDATE_BY = ?, \n");
		sql.append("    UPDATE_DATE = ?, \n");
		sql.append("    MSG_ID = null, \n");
		sql.append("    SEND_NUM = '0', \n");
		sql.append("    IS_SEND_EMAIL = '0', \n");
		sql.append("    SEND_DATE = null, \n");
		sql.append("    IS_OVER_DUE = ?, \n");//
		sql.append("    COMPANY_NAME = ?, \n");//公司名称
		sql.append("    VEHICLE_PURPOSE = ?, \n");//车辆用途
		sql.append("    ENGINE_NO = ?, \n");//发动机号
		sql.append("    IS_SALES_RETURN_STATUS = ?, \n");
		sql.append("    RECEIVE_BUILD_CONNECT_STATUS = null, \n");//接收连接状态
		sql.append("    RECEIVE_CONNECT_RETURN_MSG = null, \n");//结束连接返回消息
		sql.append("    RECEIVE_DATE = null, \n");//接收日期
		sql.append("    DATA_SOURCE = '1' \n");//接收日期
		sql.append("  WHERE  \n");
		sql.append("    DMS_OWNER_ID = '"+dmsOwnerId+"'  \n");
		sql.append("    AND DEALER_CODE = '"+dealerCode+"' \n");
		sql.append("    AND VIN = '"+sendMsgVo.getVin()+"' \n");
		List<Object> params = new ArrayList<Object>();
		params.add(sendMsgVo.getClientType());
		params.add(sendMsgVo.getName());
		params.add(sendMsgVo.getGender());
		params.add(sendMsgVo.getCellphone());
		params.add(sendMsgVo.getIdOrCompCode());
		params.add(sendMsgVo.getProvinceId());
		params.add(sendMsgVo.getCityId());
		params.add(sendMsgVo.getDistrict());
		params.add(sendMsgVo.getAddress());
		params.add(sendMsgVo.getEmail());
		params.add(sendMsgVo.getPostCode());
		params.add(sendMsgVo.getDmsOwnerId());
		params.add(dealerCode);
		params.add(sendMsgVo.getVin());
		params.add(sendMsgVo.getBrandId());
		params.add(sendMsgVo.getModeId());
		params.add(sendMsgVo.getStyleId());
		params.add(sendMsgVo.getColorId());
		params.add(sendMsgVo.getIsSecondTime());
		params.add(dcsSendBoldStatus);
		params.add(sendMsgVo.getBuyTime());
		params.add(DEConstant.DE_CREATE_BY);
		params.add(new Date());
		params.add(sendMsgVo.getIsOverDue());
		params.add(sendMsgVo.getCompanyName());
		params.add(sendMsgVo.getVehiclePurpose());
		params.add(sendMsgVo.getEngineNo());
		params.add(sendMsgVo.getIsSalesReturnStatus());
		OemDAOUtil.execBatchPreparement(sql.toString(), params);
	}
	
}
