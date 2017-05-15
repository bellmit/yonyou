package com.yonyou.dms.vehicle.service.orderManage;

import java.util.Map;

import com.yonyou.dms.vehicle.domains.DTO.orderManager.FurtherOrderRangeSettingListDTO;

public interface FurtherOrderRangeSettingService {
	/**
	 * 资源范围设定
	 * 
	 * @param paramList
	 */
	void settingRange(FurtherOrderRangeSettingListDTO paramList);

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	Map<String, Object> queryInit();

}
