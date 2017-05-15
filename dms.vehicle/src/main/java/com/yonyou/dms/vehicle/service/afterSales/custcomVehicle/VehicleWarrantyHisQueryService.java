package com.yonyou.dms.vehicle.service.afterSales.custcomVehicle;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;

/**
 * 车辆保修历史查询
 * @author Administrator
 *
 */
public interface VehicleWarrantyHisQueryService {
	//查询
	public PageInfoDto  VehicleWarrantyHisQuery(Map<String, String> queryParam);
	//车辆保修历史下载
	public void download(Map<String, String> queryParam,HttpServletResponse response, HttpServletRequest request);
	//经销商车辆保修历史下载
	public void download2(Map<String, String> queryParam,HttpServletResponse response, HttpServletRequest request);

}
