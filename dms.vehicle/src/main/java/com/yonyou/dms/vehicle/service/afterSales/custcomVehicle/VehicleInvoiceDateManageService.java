package com.yonyou.dms.vehicle.service.afterSales.custcomVehicle;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.retailReportQuery.TtVsSalesReportDTO;

/**
 * 车辆开票日期维护
 * @author Administrator
 *
 */
public interface VehicleInvoiceDateManageService {
	//车辆开票日期维护查询
	public PageInfoDto  VehicleInvoiceDateQuery(Map<String, String> queryParam);
	//车辆开票同日期维护下载
	public void download(Map<String, String> queryParam,HttpServletResponse response, HttpServletRequest request);
	//通过id查询修改的信息回显
	public Map getShuju(Long id);
	//通过id进行修改
	public void edit(Long id,TtVsSalesReportDTO dto);
}
