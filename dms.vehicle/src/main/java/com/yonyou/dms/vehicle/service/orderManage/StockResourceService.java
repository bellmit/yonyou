package com.yonyou.dms.vehicle.service.orderManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.StockResourceDTO;

public interface StockResourceService {

	PageInfoDto stockResourceQuery(Map<String, String> queryParam);

	Map<String, Object> findStockResourceById(Long id);

	/**
	 * 检查是否已被其他经销商预定
	 * 
	 * @param queryParam
	 */
	void checkstockResourceSubmited(Map<String, String> queryParam);

	/**
	 * 预定
	 * 
	 * @param stockPO
	 */
	void doSubmit(StockResourceDTO stockPO);

	Map<String, Object> findStockResource();

	/**
	 * 批量操作
	 * 
	 * @param stockPO
	 */
	void doStockSubmit(StockResourceDTO stockPO);

}
