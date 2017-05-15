package com.yonyou.dms.vehicle.service.oeminvty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface OemVehicleService {
	/**
	 * 车辆综合查询
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto oemVehicleDetailedQuery(Map<String, String> queryParam);

	/**
	 * 车辆综合查询下载
	 * 
	 * @param queryParam
	 * @return
	 */

	public void download(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request);

	/**
	 * 车辆详细查询
	 * 
	 * @param id
	 * @param resource
	 * @return
	 */
	Map<String, Object> oemVehicleVinDetailQuery(Long id);

	PageInfoDto oemVehicleDetailed(Map<String, String> queryParam, Long id);

	PageInfoDto oemVehicleDetailed(Map<String, String> queryParam);

	/**
	 * jV车场库存管理------生产订单跟踪
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto oemVehicleQuery(Map<String, String> queryParam);

	/**
	 * jV车场库存管理------生产订单跟踪下载
	 * 
	 * @param queryParam
	 * @return
	 */
	void oemVehicleQueryDownload(Map<String, String> queryParam, HttpServletResponse response,
			HttpServletRequest request);

	/**
	 * jV车场库存管理-----详细车籍查询
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto vehicleDetailQuery(Map<String, String> queryParam);

	/**
	 * jV车场库存管理-----详细车籍查询下载
	 * 
	 * @param queryParam
	 * @return
	 */
	void doDownload(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request);

	/**
	 * jV车场库存管理-----详细车籍明细查询
	 * 
	 * @param id
	 * @return
	 */
	Map<String, Object> doQueryDetail(Long id);

	/**
	 * 车辆变更节点查询
	 * 
	 * @param id
	 * @return
	 */
	PageInfoDto vehicleNodeChangeQuery(Long id);

	/**
	 * 车厂库存查询
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto doQuery(Map<String, String> queryParam);

	/**
	 * 车厂库存查询下载
	 * 
	 * @param queryParam
	 * @return
	 */
	void depotInventorydoDownload(Map<String, String> queryParam, HttpServletResponse response,
			HttpServletRequest request);

	/**
	 * 车辆变更节点查询下载
	 * 
	 * @param id
	 * @return
	 */
	void downloadDetailsl(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request);

	void areaVehicleDownload(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request);

}
