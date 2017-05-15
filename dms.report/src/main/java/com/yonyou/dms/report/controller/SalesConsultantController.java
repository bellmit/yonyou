package com.yonyou.dms.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.report.service.impl.SalesConsultantService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 销售顾问业绩统计控制类
* @author wangliang
* @date 2017年02月07日
*/

@Controller
@TxnConn
@RequestMapping("/stockManage/SalesConsultant")
public class SalesConsultantController extends BaseController{
	
	@Autowired
    SalesConsultantService salesConsultantService;
	
	@Autowired
    private ExcelGenerator excelService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
	public PageInfoDto querySalesConsultantController(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = salesConsultantService.querySalesConsultant(queryParam);
		List<Map> rows = pageInfoDto.getRows();
		Map values = new HashMap();
		values.put("USER_NAME", "合计");
		values.put("VEHICLE_COST_AMOUNT", 0.00);
		values.put("VEHICLE_PRICE", 0.00);
		values.put("VEHICLE_GAIN_AMOUNT", 0.00);
		values.put("INSURANCE_AMOUNT", 0.00);
		values.put("PART_SALES_AMOUNT", 0.00);
		values.put("PART_GAIN_AMOUNT", 0.00);
		values.put("PART_COST_AMOUNT", 0.00);
		values.put("GARNITURE_SALES_AMOUNT", 0.00);
		values.put("GARNITURE_GAIN_AMOUNT", 0.00);
		values.put("GARNITURE_COST_AMOUNT", 0.00);
		values.put("CREDIT_AMOUNT", 0.00);
		values.put("TAX_AMOUNT", 0.00);
		values.put("LICENSE_AMOUNT", 0.00);
		for(Map map : rows ) {	
			 System.err.println(map.get("VEHICLE_COST_AMOUNT").toString());
			 values.put("VEHICLE_COST_AMOUNT", Double.parseDouble(map.get("VEHICLE_COST_AMOUNT").toString())+(double)values.get("VEHICLE_COST_AMOUNT"));
			 values.put("VEHICLE_PRICE", Double.parseDouble(map.get("VEHICLE_PRICE").toString())+(double)values.get("VEHICLE_PRICE"));
			 values.put("VEHICLE_GAIN_AMOUNT", Double.parseDouble(map.get("VEHICLE_GAIN_AMOUNT").toString())+(double)values.get("VEHICLE_GAIN_AMOUNT"));
			 values.put("INSURANCE_AMOUNT", Double.parseDouble(map.get("INSURANCE_AMOUNT").toString())+(double)values.get("INSURANCE_AMOUNT"));
			 values.put("PART_SALES_AMOUNT", Double.parseDouble(map.get("PART_SALES_AMOUNT").toString())+(double)values.get("PART_SALES_AMOUNT"));
			 values.put("PART_GAIN_AMOUNT", Double.parseDouble(map.get("PART_GAIN_AMOUNT").toString())+(double)values.get("PART_GAIN_AMOUNT"));
			 values.put("PART_COST_AMOUNT", Double.parseDouble(map.get("PART_COST_AMOUNT").toString())+(double)values.get("PART_COST_AMOUNT"));
			 values.put("GARNITURE_SALES_AMOUNT", Double.parseDouble(map.get("GARNITURE_SALES_AMOUNT").toString())+(double)values.get("GARNITURE_SALES_AMOUNT"));
			 values.put("GARNITURE_GAIN_AMOUNT", Double.parseDouble(map.get("GARNITURE_GAIN_AMOUNT").toString())+(double)values.get("GARNITURE_GAIN_AMOUNT"));
			 values.put("GARNITURE_COST_AMOUNT", Double.parseDouble(map.get("GARNITURE_COST_AMOUNT").toString())+(double)values.get("GARNITURE_COST_AMOUNT"));
			 values.put("CREDIT_AMOUNT", Double.parseDouble(map.get("CREDIT_AMOUNT").toString())+(double)values.get("CREDIT_AMOUNT"));
			 values.put("TAX_AMOUNT", Double.parseDouble(map.get("TAX_AMOUNT").toString())+(double)values.get("TAX_AMOUNT"));
			 values.put("LICENSE_AMOUNT", Double.parseDouble(map.get("LICENSE_AMOUNT").toString())+(double)values.get("LICENSE_AMOUNT"));
		}
	
		rows.add(values);
		return pageInfoDto;
	}
	
	
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	  @RequestMapping(value = "/excel", method = RequestMethod.GET)
	  public void export(@RequestParam Map<String, String> queryParam, HttpServletRequest request, HttpServletResponse response) {
		  List<Map> resultList = salesConsultantService.querySalesConsultantExport(queryParam);
		  Map<String,List<Map>> excelData = new HashMap<String,List<Map>>();
		  List<Map> rows = resultList;
			Map values = new HashMap();
			values.put("USER_NAME", "合计");
			values.put("VEHICLE_COST_AMOUNT", 0.00);
			values.put("VEHICLE_PRICE", 0.00);
			values.put("VEHICLE_GAIN_AMOUNT", 0.00);
			values.put("INSURANCE_AMOUNT", 0.00);
			values.put("PART_SALES_AMOUNT", 0.00);
			values.put("PART_GAIN_AMOUNT", 0.00);
			values.put("PART_COST_AMOUNT", 0.00);
			values.put("GARNITURE_SALES_AMOUNT", 0.00);
			values.put("GARNITURE_GAIN_AMOUNT", 0.00);
			values.put("GARNITURE_COST_AMOUNT", 0.00);
			values.put("CREDIT_AMOUNT", 0.00);
			values.put("TAX_AMOUNT", 0.00);
			values.put("LICENSE_AMOUNT", 0.00);
			for(Map map : rows ) {	
				 System.err.println(map.get("VEHICLE_COST_AMOUNT").toString());
				 values.put("VEHICLE_COST_AMOUNT", Double.parseDouble(map.get("VEHICLE_COST_AMOUNT").toString())+(double)values.get("VEHICLE_COST_AMOUNT"));
				 values.put("VEHICLE_PRICE", Double.parseDouble(map.get("VEHICLE_PRICE").toString())+(double)values.get("VEHICLE_PRICE"));
				 values.put("VEHICLE_GAIN_AMOUNT", Double.parseDouble(map.get("VEHICLE_GAIN_AMOUNT").toString())+(double)values.get("VEHICLE_GAIN_AMOUNT"));
				 values.put("INSURANCE_AMOUNT", Double.parseDouble(map.get("INSURANCE_AMOUNT").toString())+(double)values.get("INSURANCE_AMOUNT"));
				 values.put("PART_SALES_AMOUNT", Double.parseDouble(map.get("PART_SALES_AMOUNT").toString())+(double)values.get("PART_SALES_AMOUNT"));
				 values.put("PART_GAIN_AMOUNT", Double.parseDouble(map.get("PART_GAIN_AMOUNT").toString())+(double)values.get("PART_GAIN_AMOUNT"));
				 values.put("PART_COST_AMOUNT", Double.parseDouble(map.get("PART_COST_AMOUNT").toString())+(double)values.get("PART_COST_AMOUNT"));
				 values.put("GARNITURE_SALES_AMOUNT", Double.parseDouble(map.get("GARNITURE_SALES_AMOUNT").toString())+(double)values.get("GARNITURE_SALES_AMOUNT"));
				 values.put("GARNITURE_GAIN_AMOUNT", Double.parseDouble(map.get("GARNITURE_GAIN_AMOUNT").toString())+(double)values.get("GARNITURE_GAIN_AMOUNT"));
				 values.put("GARNITURE_COST_AMOUNT", Double.parseDouble(map.get("GARNITURE_COST_AMOUNT").toString())+(double)values.get("GARNITURE_COST_AMOUNT"));
				 values.put("CREDIT_AMOUNT", Double.parseDouble(map.get("CREDIT_AMOUNT").toString())+(double)values.get("CREDIT_AMOUNT"));
				 values.put("TAX_AMOUNT", Double.parseDouble(map.get("TAX_AMOUNT").toString())+(double)values.get("TAX_AMOUNT"));
				 values.put("LICENSE_AMOUNT", Double.parseDouble(map.get("LICENSE_AMOUNT").toString())+(double)values.get("LICENSE_AMOUNT"));
			}
		
			rows.add(values);
		  excelData.put("销售顾问业绩统计", rows);
		  List<ExcelExportColumn> exportColmnList = new ArrayList<ExcelExportColumn>();
		  exportColmnList.add(new ExcelExportColumn("USER_NAME","销售顾问"));
		  exportColmnList.add(new ExcelExportColumn("NOT_FINISH_ORDER_COUNT","销售订单"));
		  exportColmnList.add(new ExcelExportColumn("ADD_ORDER_COUNT","新增订单"));
		  exportColmnList.add(new ExcelExportColumn("FINISHED_ORDER_COUNT","交车数量"));
		  exportColmnList.add(new ExcelExportColumn("VEHICLE_COST_AMOUNT","销售车辆总成本"));
		  exportColmnList.add(new ExcelExportColumn("VEHICLE_PRICE","车辆销售总额"));
		  exportColmnList.add(new ExcelExportColumn("VEHICLE_GAIN_AMOUNT","车辆销售利润"));
		  exportColmnList.add(new ExcelExportColumn("INSURANCE_AMOUNT","保险利润"));
		  exportColmnList.add(new ExcelExportColumn("PART_SALES_AMOUNT","装潢金额"));
		  exportColmnList.add(new ExcelExportColumn("PART_GAIN_AMOUNT","装潢利润"));
		  exportColmnList.add(new ExcelExportColumn("PART_COST_AMOUNT","装潢成本"));
		  exportColmnList.add(new ExcelExportColumn("GARNITURE_SALES_AMOUNT","精品金额"));
		  exportColmnList.add(new ExcelExportColumn("GARNITURE_GAIN_AMOUNT","精品利润"));
		  exportColmnList.add(new ExcelExportColumn("GARNITURE_COST_AMOUNT","精品成本"));
		  exportColmnList.add(new ExcelExportColumn("CREDIT_AMOUNT","信贷利润"));
		  exportColmnList.add(new ExcelExportColumn("TAX_AMOUNT","购税利润"));
		  exportColmnList.add(new ExcelExportColumn("LICENSE_AMOUNT","上牌利润"));
		  
		  //生成excel文件
		  excelService.generateExcelForDms(excelData, exportColmnList,FrameworkUtil.getLoginInfo().getDealerShortName()+"_销售顾问业绩统计.xls", request, response);
	  }
	
}
