package com.yonyou.dms.vehicle.service.afterSales.custcomVehicle;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.custcomVehicle.TtVsCustomerDTO;

/**
 * 客户资料维护
 * @author Administrator
 *
 */
public interface CustcomVehicleManageService {
	//客户资料查询
	public PageInfoDto  CustcomVehicleQuery(Map<String, String> queryParam);
	//客户资料下载
	public void download(Map<String, String> queryParam,HttpServletResponse response, HttpServletRequest request);
	//修改信息回显
	public Map getShuju(Long id);
	//查询所在行业类别
	public List<Map> searchIndustryBig();
	//查询所在行业类别2
	public List<Map> searchIndustry(String parentCode);
	//查询省份
	public List<Map> queryProvince();
	//查询市
	public List<Map> queryCity(Long parentId);
	//查询县
	public List<Map> queryTowm(Long parentId);
	//修改客户资料维护
	public void edit(Long id,TtVsCustomerDTO dto);
}
