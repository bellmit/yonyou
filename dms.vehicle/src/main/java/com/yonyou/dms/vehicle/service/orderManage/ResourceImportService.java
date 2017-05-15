package com.yonyou.dms.vehicle.service.orderManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.vehicle.domains.DTO.orderManager.ResourceOrderUploadDTO;

public interface ResourceImportService {
	/**
	 * 插入到临时表
	 * 
	 * @param rowDto
	 * @param orderType
	 */
	void insertTmp(ResourceOrderUploadDTO rowDto, String orderType);

	List<Map<String, Object>> checkData(String orderType);

	/**
	 * 临时表数据回显
	 * 
	 * @param orderType
	 * @return
	 */
	List<Map> importShow(String orderType);

	/**
	 * 导入
	 */
	void importTableAppProce();

}
