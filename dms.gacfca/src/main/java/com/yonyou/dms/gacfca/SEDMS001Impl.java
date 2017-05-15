package com.yonyou.dms.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TtOwnerChangeDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtOwnerChangePO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 计划任务
 * 				上传车主关键信息变更历史
 * @author Administrator
 *
 */
@Service
public class SEDMS001Impl implements SEDMS001{

	final Logger logger = Logger.getLogger(SEDMS001Impl.class);

	/**
	 * @description  计划任务      上传车主关键信息变更历史
	 * @param dealerCode 
	 */
	@Override
	public LinkedList<TtOwnerChangeDTO> getSEDMS001(String dealerCode) {
		logger.info("==========SEDMS001Impl执行===========");
		LinkedList<TtOwnerChangeDTO> resultList = new LinkedList<TtOwnerChangeDTO>();
		try{
			
			logger.debug("from TtOwnerChangePO UPDATE_DATE >= CURRENT_DATE and DEALER_CODE = "+dealerCode+" and IS_UPLOAD = "+CommonConstants.DICT_IS_NO);
			
			List<TtOwnerChangePO> list = TtOwnerChangePO.findBySQL("UPDATE_DATE >= CURRENT_DATE and DEALER_CODE = ? and IS_UPLOAD = ?", 
					dealerCode,CommonConstants.DICT_IS_NO);
			
			if(list != null && !list.isEmpty()){
				for (TtOwnerChangePO ttOwnerChangePO : list) {
					TtOwnerChangeDTO ttOwnerChangeVO = new TtOwnerChangeDTO();
					
					logger.debug("from TmVehiclePO DEALER_CODE = "+ttOwnerChangePO.getString("DERALE_CODE")+" AND OWNER_NO = "+ttOwnerChangePO.getString("OWNER_NO"));
					
					List<TmVehiclePO> listVehicle = TmVehiclePO.findBySQL("DEALER_CODE = ? AND OWNER_NO = ?", ttOwnerChangePO.getString("DERALE_CODE"),ttOwnerChangePO.getString("OWNER_NO"));
					TmVehiclePO getVehicle = listVehicle.get(0); //获取出车主的第一辆
					ttOwnerChangeVO.setContactorAddress(ttOwnerChangePO.getString("CONTACTOR_ADDRESS"));
					ttOwnerChangeVO.setContactorCity(ttOwnerChangePO.getString("CONTACTOR_CITY"));
					ttOwnerChangeVO.setContactorDistrict(ttOwnerChangePO.getString("CONTACTOR_DISTRICT"));
					ttOwnerChangeVO.setContactorEmail(ttOwnerChangePO.getString("CONTACTOR_EMAIL"));
					ttOwnerChangeVO.setContactorMobile(ttOwnerChangePO.getString("CONTACTOR_MOBILE"));
					ttOwnerChangeVO.setContactorName(ttOwnerChangePO.getString("CONTACTOR_NAME"));
					ttOwnerChangeVO.setContactorPhone(ttOwnerChangePO.getString("CONTACTOR_PHONE"));
					ttOwnerChangeVO.setContactorProvince(ttOwnerChangePO.getInteger("CONTACTOR_PROVINCE"));
					ttOwnerChangeVO.setOwnerName(ttOwnerChangePO.getString("OWNER_NAME"));
					ttOwnerChangeVO.setOwnerNo(ttOwnerChangePO.getString("OWNER_NO"));
					ttOwnerChangeVO.setDealerCode(ttOwnerChangePO.getString("DEALER_CODE"));
					ttOwnerChangeVO.setVin(getVehicle.getString("VIN"));
					resultList.add(ttOwnerChangeVO);
					
					logger.debug("updete TtOwnerChangePO set IS_UPLOAD = "+Utility.getInt(CommonConstants.DICT_IS_YES)+",UPDATE_DATE = "+new Date()+" where ITEM_ID = "+ttOwnerChangePO.getString("ItemId")+" and OWNER_NO = "+ttOwnerChangePO.getString("OwnerNo"));
					
					TtOwnerChangePO.update("IS_UPLOAD = ?,UPDATE_DATE = ?", 
							"ITEM_ID = ? and OWNER_NO = ?", 
							Utility.getInt(CommonConstants.DICT_IS_YES),new Date(),ttOwnerChangePO.getString("ITEM_ID"),ttOwnerChangePO.getString("OWNER_NO"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e);
		}finally{
			logger.info("==========SEDMS001Impl结束===========");
		}
		return resultList;
	}

}
