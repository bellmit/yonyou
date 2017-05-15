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
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.report.service.impl.SearchTestDriveDetailService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/saleReport/SearchTestDriveDetail")
public class SearchTestDriveDetailController extends BaseController {
	 @Autowired
	 SearchTestDriveDetailService  searchTestDriveDetailService;
	 @Autowired
	 private ExcelGenerator excelService;
	
	  @RequestMapping(method = RequestMethod.GET)
	  @ResponseBody
	  public PageInfoDto queryTestDriveDetail(@RequestParam Map<String,String> queryParam) {
		  PageInfoDto pageInfoDto = searchTestDriveDetailService.queryTestDriveDetail(queryParam);
		  return pageInfoDto;
	  }
		  
	@RequestMapping(value="/export/excel",method = RequestMethod.GET)
	public void excel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
	                  HttpServletResponse response){
	    List<Map> resultList =searchTestDriveDetailService.querySafeToExport(queryParam);
	    Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
	    excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_试乘试驾明细", resultList);
	    List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
	    exportColumnList.add(new ExcelExportColumn("USER_NAME","销售顾问"));
	    exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO","客户编号"));
	    exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME","客户名称"));
	    exportColumnList.add(new ExcelExportColumn("GENDER","性别",ExcelDataType.Dict));
	    exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE","联系电话"));
	    exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE","联系手机"));
	    exportColumnList.add(new ExcelExportColumn("ARRIVE_TIME","到店日期"));
	    exportColumnList.add(new ExcelExportColumn("SERIES_NAME_1","试驾车系"));
	    exportColumnList.add(new ExcelExportColumn("IS_REGISTER","是否登记",ExcelDataType.Dict));
	    exportColumnList.add(new ExcelExportColumn("TEST_DRIVE_REGISTER","登记日期",CommonConstants.SIMPLE_DATE_FORMAT));
	    exportColumnList.add(new ExcelExportColumn("TEST_DRIVE_REGISTER","登记时间",CommonConstants.FULL_DATE_TIME_FORMAT));
	    exportColumnList.add(new ExcelExportColumn("TEST_DRIVE_TYPE","体验内容",ExcelDataType.Dict));
	    exportColumnList.add(new ExcelExportColumn("PLACE","试驾地点",ExcelDataType.Dict));
	    exportColumnList.add(new ExcelExportColumn("IS_FEEDBACK","是否填写反馈表",ExcelDataType.Dict));
	    exportColumnList.add(new ExcelExportColumn("SCORE","客户评分"));
	    exportColumnList.add(new ExcelExportColumn("TEST_DRIVE_FEEDBACK","反馈时间",CommonConstants.FULL_DATE_TIME_FORMAT));
	    exportColumnList.add(new ExcelExportColumn("IS_STOCK_OUT","是否成交",ExcelDataType.Dict));
	    exportColumnList.add(new ExcelExportColumn("SERIES_NAME_2","成交车系"));
	    exportColumnList.add(new ExcelExportColumn("STOCK_OUT_DATE","成交日期"));
	    exportColumnList.add(new ExcelExportColumn("TEST_DRIVE_REMARK","备注"));
	    
	    
	    excelService.generateExcel(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_试乘试驾明细.xls", request, response);
	}
}
