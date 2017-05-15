package com.yonyou.dms.report.controller;


import java.text.NumberFormat;
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
import com.yonyou.dms.report.service.impl.SalesStatisticsService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
* 销售统计分析报表控制类
* @author wangliang
* @date 2017年01月11日
*/
@Controller
@TxnConn
@RequestMapping("/saleReport/SalesStatistics")
public class SalesStatisticsController extends BaseController {
	 @Autowired
	 SalesStatisticsService  salesStaticsService;
	 
	  @Autowired
	  private ExcelGenerator      excelService;
	  
	  @SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	  @RequestMapping(method = RequestMethod.GET)
	  @ResponseBody
	  public PageInfoDto queryVehicleSalesed(@RequestParam Map<String,String> queryParam) {
		  PageInfoDto pageInfoDto = salesStaticsService.querySalesStatisticsChecked(queryParam);
		  
		  List<Map> rows = pageInfoDto.getRows();//获取当前页所有数据
		  Map values = new HashMap();
		  values.put("ORG_NAME", "总计");
		  values.put("ADD_TOTAL", 0.00);
		  values.put("HSL_REPLACE",0.00);
		  values.put("ACCOUNT_TOTAL",0.00);
		  values.put("PERMUTED_TOTAL",0.00);
		
		  for(Map map : rows){
			  values.put("ADD_TOTAL", Double.parseDouble(map.get("ADD_TOTAL").toString())+(double)values.get("ADD_TOTAL"));
			  values.put("HSL_REPLACE", Double.parseDouble(map.get("HSL_REPLACE").toString())+(double)values.get("HSL_REPLACE"));
			  values.put("ACCOUNT_TOTAL",  Double.parseDouble(map.get("ACCOUNT_TOTAL").toString())+(double)values.get("ACCOUNT_TOTAL"));
			  values.put("PERMUTED_TOTAL",  Double.parseDouble(map.get("PERMUTED_TOTAL").toString())+(double)values.get("PERMUTED_TOTAL"));
		  }
		  
		  double e = (double)values.get("HSL_REPLACE")/(double)values.get("ADD_TOTAL");
		  NumberFormat nt = NumberFormat.getPercentInstance();
		  nt.setMinimumFractionDigits(2);
		 
		  values.put("REPLACE_RATE", nt.format(e));
		  if((double)values.get("PERMUTED_TOTAL")!=0) {
			  double d = (double)values.get("PERMUTED_TOTAL")/(double)values.get("ACCOUNT_TOTAL");
			  values.put("PERMUTED_RATE", nt.format(d));
		  }else {
			  values.put("PERMUTED_RATE", "0.00%");
		  }
		  
		  values.put("REPLACE_RATE", nt.format(e));
		  
		  rows.add(values);
		 
		  return pageInfoDto;
	  }
	  
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	  @RequestMapping(value = "/excel", method = RequestMethod.GET)
	  public void export(@RequestParam Map<String, String> queryParam, HttpServletRequest request, HttpServletResponse response) {
		  List<Map> resultList = salesStaticsService.querySalesStaticExport(queryParam);
		  Map<String,List<Map>> excelData = new HashMap<String,List<Map>>();
		  List<Map> rows = resultList;
		  Map values = new HashMap();
		  values.put("ORG_NAME", "总计");
		  values.put("ADD_TOTAL", 0.00);
		  values.put("HSL_REPLACE",0.00);
		  values.put("ACCOUNT_TOTAL",0.00);
		  values.put("PERMUTED_TOTAL",0.00);
		
		  for(Map map : rows){
			  values.put("ADD_TOTAL", Double.parseDouble(map.get("ADD_TOTAL").toString())+(double)values.get("ADD_TOTAL"));
			  values.put("HSL_REPLACE", Double.parseDouble(map.get("HSL_REPLACE").toString())+(double)values.get("HSL_REPLACE"));
			  values.put("ACCOUNT_TOTAL",  Double.parseDouble(map.get("ACCOUNT_TOTAL").toString())+(double)values.get("ACCOUNT_TOTAL"));
			  values.put("PERMUTED_TOTAL",  Double.parseDouble(map.get("PERMUTED_TOTAL").toString())+(double)values.get("PERMUTED_TOTAL"));
		  }
		  
		  double e = (double)values.get("HSL_REPLACE")/(double)values.get("ADD_TOTAL");
		  NumberFormat nt = NumberFormat.getPercentInstance();
		  nt.setMinimumFractionDigits(2);
		 
		  values.put("REPLACE_RATE", nt.format(e));
		  if((double)values.get("PERMUTED_TOTAL")!=0) {
			  double d = (double)values.get("PERMUTED_TOTAL")/(double)values.get("ACCOUNT_TOTAL");
			  values.put("PERMUTED_RATE", nt.format(d));
		  }else {
			  values.put("PERMUTED_RATE", "0.00%");
		  }
		  
		  values.put("REPLACE_RATE", nt.format(e));
		  
		  rows.add(values);
		  
		  excelData.put("销售顾问业绩统计", rows);
		  List<ExcelExportColumn> exportColmnList = new ArrayList<ExcelExportColumn>();
		  exportColmnList.add(new ExcelExportColumn("ORG_NAME","销售小组"));
		  exportColmnList.add(new ExcelExportColumn("USER_NAME","销售顾问"));
		  exportColmnList.add(new ExcelExportColumn("ADD_TOTAL","新建潜在客户数"));
		  exportColmnList.add(new ExcelExportColumn("HSL_REPLACE","实销数量"));
		  exportColmnList.add(new ExcelExportColumn("PERMUTED_TOTAL","置换成交数"));
		  exportColmnList.add(new ExcelExportColumn("PERMUTED_RATE","置换成交率"));
		  exportColmnList.add(new ExcelExportColumn("REPLACE_RATE","置换意向占比"));
		  exportColmnList.add(new ExcelExportColumn("CONVER_RATE","置换转化率"));
		  //生成excel文件
		  excelService.generateExcel(excelData, exportColmnList,"销售顾问业绩统计.xls", request, response);
	  }
}
