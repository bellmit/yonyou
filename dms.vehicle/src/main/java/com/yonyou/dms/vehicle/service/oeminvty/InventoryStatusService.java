package com.yonyou.dms.vehicle.service.oeminvty;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface InventoryStatusService {
	/**
	 * 车长库存汇总查询
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto inventoryStatusQueryCollect(Map<String, String> queryParam);

	/**
	 * 明细查询
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto inventoryStatusQueryDetails(Map<String, String> queryParam);

	/**
	 * 车厂明细下载
	 * 
	 * @param queryParam
	 * @param response
	 * @param request
	 */
	void downloadDetailsl(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request);

	List<Map> findnodeStatus(Long id);

}
