package com.yonyou.dms.vehicle.service.afterSales.custcomVehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtVsSalesReportPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.vehicle.dao.afterSales.custcomVehicle.VehicleInvoiceDateManageDao;
import com.yonyou.dms.vehicle.domains.DTO.retailReportQuery.TtVsSalesReportDTO;
/**
 * 车辆开票日期维护
 * @author Administrator
 *
 */
@Service
public class VehicleInvoiceDateManageServiceImpl implements VehicleInvoiceDateManageService{
	@Autowired
	VehicleInvoiceDateManageDao  vehicleInvoiceDateManageDao;
	@Autowired
	private ExcelGenerator excelService;
	/**
	 * 车辆开票日期维护查询
	 */

	@Override
	public PageInfoDto VehicleInvoiceDateQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return vehicleInvoiceDateManageDao.VehicleInvoiceDateQuery(queryParam);
	}
	/**
	 * 车辆开票日期维护下载
	 */
	@Override
	public void download(Map<String, String> queryParam,HttpServletResponse response, HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = vehicleInvoiceDateManageDao.download(queryParam);
		// TODO
		excelData.put("资源分配备注下载", (List<Map>) list);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("BIG_AREA_NAME", "大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA_NAME", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商"));
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "车辆用途"));
		
		exportColumnList.add(new ExcelExportColumn("INVOICE_DATE", "开票日期"));
		exportColumnList.add(new ExcelExportColumn("REMARK", "车辆分配备注"));
		excelService.generateExcel(excelData, exportColumnList, "车辆开票日期维护信息下载.xls", request, response);
	}
	/**
	 * 修改信息回显
	 */
	@Override
	public Map getShuju(Long id) {
		 Map<String, Object> m=new HashMap<String, Object>();
    	 List<Map> list=vehicleInvoiceDateManageDao.getShuju(id);
    	 m= list.get(0);
    	   return m;
	}
	/**
	 * 通过id进行修改车辆开票日期
	 */
	@Override
	public void edit(Long id, TtVsSalesReportDTO dto) {
		TtVsSalesReportPO  po=TtVsSalesReportPO.findById(id);
		po.setDate("INVOICE_DATE", dto.getInvoiceDate());
		po.saveIt();
	}
	
	

}
