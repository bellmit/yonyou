package com.yonyou.dms.gacfca;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.ServiceVehicleDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.f4.de.DEMessage;

/**
 * @description 车主车辆新增车辆同步获取厂端数据
 * @author Administrator
 *
 */
@Service
public class SEDMS004CoudImpl implements SEDMS004Coud {

	final Logger logger = Logger.getLogger(SEDMS004CoudImpl.class);

	/**
	 * @description 车主车辆新增车辆同步获取厂端数据
	 * @param vin
	 */
	@Override
	public LinkedList<ServiceVehicleDTO> getSEDMS004(String dealerCode,String vin,String timeOut) {
		return null;
//		try{
//			LinkedList<ServiceVehicleDTO> rsList = new LinkedList<ServiceVehicleDTO>();
//			ServiceVehicleDTO vo = new ServiceVehicleDTO();
//			vo.setVin(vin);
//			rsList.add(vo);
//			DEMessage msgReturn = null;
//
//			// 发送消息
//			// 同步接口，超时设定
//			if (!((timeOut != null) && (!"".equals(timeOut)) && (Utility.getInt(timeOut) > 0))) {
//				//  待引入接口
////				msgReturn = DeUtility.sendSyncMessage(atx.getConnection(), "SEDCS004", entityCode, rsList);
//			} else {
//				//  待引入接口
////				msgReturn = DeUtility.sendSyncMessageWithTimeOut(atx, "SEDCS004", entityCode, rsList, Utility.getInt(timeOut));
//			}
//
//			if (msgReturn != null) {
//				LinkedList voList = (LinkedList) msgReturn.getContent();
//				List<DynaBean> resultList = new ArrayList<DynaBean>();
//				if (voList != null && voList.size() > 0) {
//					for (int i = 0; i < voList.size(); i++) {
//						ServiceVehicleDTO vehicleVO = (ServiceVehicleDTO) voList.get(i);
//						DynaBean bean = new DynaBean("TM_VEHICLE");
//
//						if(!Utility.testString(vehicleVO.getYear())){
//							atx.setStringValue("IS_SEDMS004", "Y");
//						}
//						logger.debug("wei xin =="+vehicleVO.getIsBinding());
//						logger.debug("wei xin =="+vehicleVO.getBindingDate());
//
//						bean.add("VIN", vehicleVO.getYear());//车型年共用字段信息
//						bean.add("MILEAGE", vehicleVO.getMileage());
//						// 为了匹配产品，加上其他字段
//						bean.add("BRAND", vehicleVO.getBrandCode());
//						bean.add("SERIES", vehicleVO.getSeriesCode());
//						bean.add("MODEL", vehicleVO.getModelCode());
//						bean.add("LICENSE", vehicleVO.getLicenseNo());
//						bean.add("COLOR", vehicleVO.getColorCode());
//						bean.add("ENGINE_NO", vehicleVO.getEngineNo());
//						bean.add("OWNER_NAME", vehicleVO.getMaterialCode());//配置共用字段信息
//						bean.add("TOTAL_CHANGE_MILEAGE", null);
//						bean.add("LICENSE_DATE", null);
//						bean.add("SALES_DATE", vehicleVO.getSaleDate());
//						bean.add("CHANGE_DATE", null);
//						bean.add("LAST_MAINTAIN_DATE", null);
//						bean.add("MODEL_YEAR", vehicleVO.getYear());
//						bean.add("VEHICLE_PURPOSE", vehicleVO.getVehiclePurpose());
//						if("1".equals(vehicleVO.getIsBinding())){
//							bean.add("IS_BINDING", DictDataConstant.DICT_IS_YES);
//						}else{
//							bean.add("IS_BINDING", DictDataConstant.DICT_IS_NO);
//						}
//
//						bean.add("BINDING_DATE", vehicleVO.getBindingDate());
//
//						resultList.add(bean);
//					}
//					atx.setArrayValue("TM_VEHICLE", resultList.toArray());
//				}else{
//					atx.setStringValue("IS_SEDMS004", "Y");
//				}
//
//			} else {
//				atx.setStringValue("IS_SEDMS004", "Y");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 0;
//		}finally{
//
//		}
	}
}
