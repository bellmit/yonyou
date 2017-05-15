package com.yonyou.dms.gacfca;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.ActivityDTO;
import com.yonyou.dms.DTO.gacfca.ActivityVehicleDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityResultPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityVehiclePO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 服务活动完成情况下发
 * @author Administrator
 *
 */
@Service
public class SEDMS009Impl implements SEDMS009 {
	final Logger logger = Logger.getLogger(SEDMS009Impl.class);

	@Override
	public int getSEDMS009(String dealerCode, List<ActivityDTO> activityResultList) {
		logger.info("==========SEDMS009Impl执行===========");
		try {
			if (activityResultList != null && !activityResultList.isEmpty()) {
				for (ActivityDTO activityDTO : activityResultList) {
					String activityCode = activityDTO.getActivityCode();
					Date downTimestamp = activityDTO.getDownTimestamp();
					String activityName = activityDTO.getActivityName();
					if (activityCode == null || "".equals(activityCode.trim())) {
						logger.debug("服务活动编号为空");
						throw new Exception("服务活动编号为空");
					}
					activityCode = activityCode.length()>14 ? activityCode=activityCode.substring(activityCode.length()-14) : activityCode; 
					TtActivityPO activityPO = getActivityPO(dealerCode, activityCode);
					if (activityPO != null) {
						LinkedList<ActivityVehicleDTO> vehicleList = activityDTO.getVehicleVoList();
						if (vehicleList != null && !vehicleList.isEmpty()) {
							for (ActivityVehicleDTO vehicleVO : vehicleList) {
								maintainActivityResult(dealerCode, activityCode, activityName, downTimestamp, vehicleVO);
								maintainActivityVehicle(dealerCode, activityCode, downTimestamp, vehicleVO);
							}
						}
					}
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return 0;
		}finally{
			logger.info("==========SEDMS009Impl结束===========");
		}
	}

	/**
	 * @description 获取 TtActivityPO
	 * @param dealerCode
	 * @param activityCode
	 * @return
	 */
	private TtActivityPO getActivityPO(String dealerCode, String activityCode) {
		logger.debug("from TtActivityPO DEALER_CODE = "+dealerCode+" and ACTIVITY_CODE = "+activityCode+" and D_KEY = "+CommonConstants.D_KEY);
		List<TtActivityPO> list =TtActivityPO.findBySQL("DEALER_CODE = ? and ACTIVITY_CODE = ? and D_KEY = ?", dealerCode,activityCode,CommonConstants.D_KEY);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * @description 接受服务活动信息，并保存到数据库
	 * @param dealerCode
	 * @param activityCode
	 * @param activityName
	 * @param downTimestamp
	 * @param activityVehicleDTO
	 */
	private void maintainActivityResult(String dealerCode, String activityCode, String activityName, Date downTimestamp, ActivityVehicleDTO activityVehicleDTO) {
		TtActivityResultPO ttActivityResultPO = new TtActivityResultPO();
		ttActivityResultPO.setString("DEALER_CODE",dealerCode);
		ttActivityResultPO.setString("ACTIVITY_CODE",activityCode);
		ttActivityResultPO.setString("ACTIVITY_NAME",activityName);
		ttActivityResultPO.setString("VIN",activityVehicleDTO.getVin());
		ttActivityResultPO.setString("DEALER_NAME",activityVehicleDTO.getRealEntityName());
		ttActivityResultPO.setDate("CAMPAIGN_DATE",activityVehicleDTO.getCampaignDate());
		ttActivityResultPO.setDate("DOWN_TIMESTAMP",downTimestamp);
		ttActivityResultPO.setInteger("D_KEY",CommonConstants.D_KEY);
		ttActivityResultPO.setString("CREATE_BY","1");
		ttActivityResultPO.setDate("CREATE_AT",new Date());
		ttActivityResultPO.saveIt();
	}
	
	/**
	 * @description 更新参加优惠的活动车型
	 * @param dealerCode
	 * @param activityCode
	 * @param downTimestamp
	 * @param activityVehicleDTO
	 */
	private void maintainActivityVehicle(String dealerCode, String activityCode, Date downTimestamp, ActivityVehicleDTO activityVehicleDTO) {
		TtActivityVehiclePO.update("REAL_ENTITY = ?, IS_ACHIEVE = ?,Repair_Date = ?,UPDATE_ID = ?,UPDATE_AT =?",
				"DEALER_CODE = ? and ACTIVITY_CODE = ? and VIN = ? and D_KEY = ?",
				activityVehicleDTO.getRealEntityCode(),CommonConstants.DICT_IS_YES,activityVehicleDTO.getCampaignDate(),
				activityVehicleDTO.getCampaignDate(),"1",new Date(),dealerCode,activityCode,activityVehicleDTO.getVin(),CommonConstants.D_KEY);
		
	}
}
