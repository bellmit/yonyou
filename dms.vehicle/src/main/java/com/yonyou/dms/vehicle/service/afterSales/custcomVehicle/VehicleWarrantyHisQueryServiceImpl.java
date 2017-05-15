package com.yonyou.dms.vehicle.service.afterSales.custcomVehicle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.vehicle.dao.afterSales.custcomVehicle.VehicleWarrantyHisQueryDao;

/**
 * 车辆保修历史查询
 * @author Administrator
 *
 */
@Service
public class VehicleWarrantyHisQueryServiceImpl implements VehicleWarrantyHisQueryService{
	@Autowired
	VehicleWarrantyHisQueryDao  vehicleWarrantyHisQueryDao;
	@Autowired
	private ExcelGenerator excelService;

	/**
	 * 查询  
	 */
	@Override
	public PageInfoDto VehicleWarrantyHisQuery(Map<String, String> queryParam) {
		return vehicleWarrantyHisQueryDao.VehicleWarrantyHisQuery(queryParam);
	}
		/**
		 * 车辆保修历史下载
		 */
	@Override
	public void download(Map<String, String> queryParam,HttpServletResponse response, HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = vehicleWarrantyHisQueryDao.download(queryParam);
		excelData.put("资源分配备注下载", (List<Map>) list);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("CLAIM_NUMBER", "索赔单号"));
		exportColumnList.add(new ExcelExportColumn("REPAIR_NO", "维修工单号"));
		exportColumnList.add(new ExcelExportColumn("REPAIR_TYPE", "维修类型"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("BUYDEALERNAME", "购车经销商"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("LICENSE_NO", "车牌号"));
		exportColumnList.add(new ExcelExportColumn("MILEAGE", "里程数"));
		exportColumnList.add(new ExcelExportColumn("MAIN_PART", "发动机号"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户"));
		exportColumnList.add(new ExcelExportColumn("REPAIR_DATE_CHAR", "维修日期"));
		excelService.generateExcel(excelData, exportColumnList, "资源分配备注下载.xls", request, response);
	}
	/**
	 * 经销商车辆保修历史下载
	 */
@Override
public void download2(Map<String, String> queryParam,HttpServletResponse response, HttpServletRequest request) {
	Map<String, List<Map>> excelData = new HashMap<>();
	List<Map> list = vehicleWarrantyHisQueryDao.download(queryParam);
	excelData.put("资源分配备注下载", (List<Map>) list);
	List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
	exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
	exportColumnList.add(new ExcelExportColumn("CLAIM_NUMBER", "索赔单号"));
	exportColumnList.add(new ExcelExportColumn("REPAIR_NO", "维修工单号"));
	exportColumnList.add(new ExcelExportColumn("REPAIR_TYPE", "维修类型"));
	exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
	exportColumnList.add(new ExcelExportColumn("BUYDEALERNAME", "购车经销商"));
	exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
	
	exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
	exportColumnList.add(new ExcelExportColumn("LICENSE_NO", "车牌号"));
	exportColumnList.add(new ExcelExportColumn("MILEAGE", "里程数"));
	exportColumnList.add(new ExcelExportColumn("MAIN_PART", "发动机号"));
	exportColumnList.add(new ExcelExportColumn("REPAIR_DATE_CHAR", "维修日期"));
	excelService.generateExcel(excelData, exportColumnList, "资源分配备注下载.xls", request, response);
}
}
