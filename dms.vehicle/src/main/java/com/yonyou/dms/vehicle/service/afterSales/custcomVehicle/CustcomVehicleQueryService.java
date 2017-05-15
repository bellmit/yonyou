package com.yonyou.dms.vehicle.service.afterSales.custcomVehicle;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

/**
 * 客户车辆资料查询
 * @author Administrator
 *
 */
public interface CustcomVehicleQueryService {
	//客户车辆资料查询
	public PageInfoDto  VehicleZiLiaoQuery(Map<String, String> queryParam);
	//通过id进行明细查询车辆信息
	public Map getVehicle(Long id);
	//通过id进行明细查询客户信息
	public Map getCustomer(Long id);
	//通过id进行明细查询联系人信息
	public Map getPeople(Long id);
	// 通过id进行查询二手车信息
	public Map getCheXinXi(Long id);
	

}
