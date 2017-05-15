
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : SalesPerformanceController.java
*
* @Author : zhanshiwei
*
* @Date : 2017年2月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月17日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.report.service.impl.SalesPerformanceService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 销售业绩一览报表
 * 
 * @author zhanshiwei
 * @date 2017年2月17日
 */
@Controller
@TxnConn
@RequestMapping("/sales/performance")
public class SalesPerformanceController extends BaseController {

    @Autowired
    private SalesPerformanceService salesPerformanceservice;
    
    @Autowired
    private ExcelGenerator      excelService;

    /**
     * 销售业绩一览报表查询
     * 
     * @author zhanshiwei
     * @date 2017年2月17日
     * @param queryParam
     * @return
     */

    @SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querySalesPerformance(@RequestParam Map<String, String> queryParam) {
        PageInfoDto id = salesPerformanceservice.querySalesPerformance(queryParam);
        List<Map> rows = id.getRows();//获取当前页所有数据
        Map values = new HashMap();//留档
        values.put("SOLD_BY", "总计");
        values.put("GOAL_SALES", 0.00);
        values.put("REAL_SALES", 0.00);
        values.put("RATE_SALES","");
        values.put("JINGPIN_COUNT", 0.00);
        values.put("JINGPIN_AMOUNT", 0.00);
        values.put("JINGPIN_COST", 0.00);
        values.put("SALES_AMOUNT", 0.00);
        values.put("INSURANCE", 0.00);
        values.put("FINANCE", 0.00);
        values.put("OLD_CAR", 0.00);
        values.put("H_CUSTOMER", 0.00);
        values.put("A_CUSTOMER", 0.00);
        values.put("B_CUSTOMER", 0.00);
        values.put("C_CUSTOMER", 0.00);
        values.put("N_CUSTOMER", 0.00);
        values.put("CREATE5", "");
        values.put("TEST", ""); 
        values.put("BACK", "");
        values.put("DEAL", "");
        values.put("TRANS", "");
        values.put("CREATE_RATE", "");
        values.put("TEST_RATE","");
        values.put("BACK_RATE", "");
        values.put("DEAL_RATE", "");
        values.put("TRANS_RATE","");
        values.put("DCC_CUSTOMER", 0.00);
        values.put("COMPLAIN_COUNT", 0.00);
        for (Map map : rows) {
        	values.put("GOAL_SALES", (double)values.get("GOAL_SALES")+Double.parseDouble(map.get("GOAL_SALES").toString()));
        	values.put("REAL_SALES", (double)values.get("REAL_SALES")+Double.parseDouble(map.get("REAL_SALES").toString()));
        	values.put("JINGPIN_COUNT", (double)values.get("JINGPIN_COUNT")+Double.parseDouble(map.get("JINGPIN_COUNT").toString()));
        	values.put("JINGPIN_AMOUNT", (double)values.get("JINGPIN_AMOUNT")+Double.parseDouble(map.get("JINGPIN_AMOUNT").toString()));
        	values.put("JINGPIN_COST", (double)values.get("JINGPIN_COST")+Double.parseDouble(map.get("JINGPIN_COST").toString()));
        	values.put("SALES_AMOUNT", (double)values.get("SALES_AMOUNT")+Double.parseDouble(map.get("SALES_AMOUNT").toString()));
        	values.put("INSURANCE", (double)values.get("INSURANCE")+Double.parseDouble(map.get("INSURANCE").toString()));
        	values.put("FINANCE", (double)values.get("FINANCE")+Double.parseDouble(map.get("FINANCE").toString()));
        	values.put("OLD_CAR", (double)values.get("OLD_CAR")+Double.parseDouble(map.get("OLD_CAR").toString()));
        	values.put("H_CUSTOMER", (double)values.get("H_CUSTOMER")+Double.parseDouble(map.get("H_CUSTOMER").toString()));
        	values.put("A_CUSTOMER", (double)values.get("A_CUSTOMER")+Double.parseDouble(map.get("A_CUSTOMER").toString()));
        	values.put("B_CUSTOMER", (double)values.get("B_CUSTOMER")+Double.parseDouble(map.get("B_CUSTOMER").toString()));
        	values.put("C_CUSTOMER", (double)values.get("C_CUSTOMER")+Double.parseDouble(map.get("C_CUSTOMER").toString()));
			values.put("N_CUSTOMER", (double)values.get("N_CUSTOMER")+Double.parseDouble(map.get("N_CUSTOMER").toString()));
			values.put("DCC_CUSTOMER", (double)values.get("DCC_CUSTOMER")+Double.parseDouble(map.get("DCC_CUSTOMER").toString()));
			values.put("COMPLAIN_COUNT", (double)values.get("COMPLAIN_COUNT")+Double.parseDouble(map.get("COMPLAIN_COUNT").toString()));
		}
        rows.add(values);
        return id;
    }

    
    /**
    * 销售业绩一览明细
    * @author zhanshiwei
    * @date 2017年2月17日
    * @param queryParam
    * @return
    */
    	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "{soldBy}/item/{yearMonth}/dict", method = RequestMethod.GET)
    @ResponseBody
    public  PageInfoDto querySalesPerformanceDetail(@RequestParam Map<String, String> queryParam,@PathVariable(value = "soldBy") String soldBy,@PathVariable(value = "yearMonth") String yearMonth) {
        queryParam.put("soldBy", soldBy);
        queryParam.put("yearMonth", yearMonth);
        PageInfoDto result=salesPerformanceservice.querySalesPerformanceDetail( queryParam);
        List<Map> rows = result.getRows();//获取当前页所有数据
        Map values = new HashMap();//留档
        values.put("SERIES_NAME", "总计");
        values.put("REAL_SALES", 0.00);
        values.put("JINGPIN_COUNT", 0.00);
        values.put("JINGPIN_AMOUNT", 0.00);
        values.put("JINGPIN_COST", 0.00);
        values.put("INSURANCE", 0.00);
        values.put("FINANCE", 0.00);
        values.put("OLD_CAR", 0.00);
        values.put("H_CUSTOMER", 0.00);
        values.put("A_CUSTOMER", 0.00);
        values.put("B_CUSTOMER", 0.00);
        values.put("C_CUSTOMER", 0.00);
        values.put("N_CUSTOMER", 0.00);
        values.put("CREATE5", "");
        values.put("TEST", ""); 
        values.put("BACK", "");
        values.put("DEAL", "");
        values.put("TRANS", "");
        values.put("CREATE_RATE", "");
        values.put("TEST_RATE","");
        values.put("BACK_RATE", "");
        values.put("DEAL_RATE", "");
        values.put("TRANS_RATE","");
        for (Map map : rows) {
        	values.put("REAL_SALES", (double)values.get("REAL_SALES")+Double.parseDouble(map.get("REAL_SALES").toString()));
        	values.put("JINGPIN_COUNT", (double)values.get("JINGPIN_COUNT")+Double.parseDouble(map.get("JINGPIN_COUNT").toString()));
        	values.put("JINGPIN_AMOUNT", (double)values.get("JINGPIN_AMOUNT")+Double.parseDouble(map.get("JINGPIN_AMOUNT").toString()));
        	values.put("JINGPIN_COST", (double)values.get("JINGPIN_COST")+Double.parseDouble(map.get("JINGPIN_COST").toString()));
        	values.put("INSURANCE", (double)values.get("INSURANCE")+Double.parseDouble(map.get("INSURANCE").toString()));
        	values.put("FINANCE", (double)values.get("FINANCE")+Double.parseDouble(map.get("FINANCE").toString()));
        	values.put("OLD_CAR", (double)values.get("OLD_CAR")+Double.parseDouble(map.get("OLD_CAR").toString()));
        	values.put("H_CUSTOMER", (double)values.get("H_CUSTOMER")+Double.parseDouble(map.get("H_CUSTOMER").toString()));
        	values.put("A_CUSTOMER", (double)values.get("A_CUSTOMER")+Double.parseDouble(map.get("A_CUSTOMER").toString()));
        	values.put("B_CUSTOMER", (double)values.get("B_CUSTOMER")+Double.parseDouble(map.get("B_CUSTOMER").toString()));
        	values.put("C_CUSTOMER", (double)values.get("C_CUSTOMER")+Double.parseDouble(map.get("C_CUSTOMER").toString()));
			values.put("N_CUSTOMER", (double)values.get("N_CUSTOMER")+Double.parseDouble(map.get("N_CUSTOMER").toString()));
		}
        rows.add(values);
        return result;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public void export(@RequestParam Map<String, String> queryParam, HttpServletRequest request, HttpServletResponse response ) {
    	List<Map> resultList = salesPerformanceservice.SalesPerformanceExprot(queryParam);
    	 List<Map> rows = resultList;//获取当前页所有数据
         Map values = new HashMap();//留档
         values.put("SOLD_BY", "总计");
         values.put("GOAL_SALES", 0.00);
         values.put("REAL_SALES", 0.00);
         values.put("RATE_SALES","");
         values.put("JINGPIN_COUNT", 0.00);
         values.put("JINGPIN_AMOUNT", 0.00);
         values.put("JINGPIN_COST", 0.00);
         values.put("SALES_AMOUNT", 0.00);
         values.put("INSURANCE", 0.00);
         values.put("FINANCE", 0.00);
         values.put("OLD_CAR", 0.00);
         values.put("H_CUSTOMER", 0.00);
         values.put("A_CUSTOMER", 0.00);
         values.put("B_CUSTOMER", 0.00);
         values.put("C_CUSTOMER", 0.00);
         values.put("N_CUSTOMER", 0.00);
         values.put("CREATE5", "");
         values.put("TEST", ""); 
         values.put("BACK", "");
         values.put("DEAL", "");
         values.put("TRANS", "");
         values.put("CREATE_RATE", "");
         values.put("TEST_RATE","");
         values.put("BACK_RATE", "");
         values.put("DEAL_RATE", "");
         values.put("TRANS_RATE","");
         values.put("DCC_CUSTOMER", 0.00);
         values.put("COMPLAIN_COUNT", 0.00);
         for (Map map : rows) {
         	values.put("GOAL_SALES", (double)values.get("GOAL_SALES")+Double.parseDouble(map.get("GOAL_SALES").toString()));
         	values.put("REAL_SALES", (double)values.get("REAL_SALES")+Double.parseDouble(map.get("REAL_SALES").toString()));
         	values.put("JINGPIN_COUNT", (double)values.get("JINGPIN_COUNT")+Double.parseDouble(map.get("JINGPIN_COUNT").toString()));
         	values.put("JINGPIN_AMOUNT", (double)values.get("JINGPIN_AMOUNT")+Double.parseDouble(map.get("JINGPIN_AMOUNT").toString()));
         	values.put("JINGPIN_COST", (double)values.get("JINGPIN_COST")+Double.parseDouble(map.get("JINGPIN_COST").toString()));
         	values.put("SALES_AMOUNT", (double)values.get("SALES_AMOUNT")+Double.parseDouble(map.get("SALES_AMOUNT").toString()));
         	values.put("INSURANCE", (double)values.get("INSURANCE")+Double.parseDouble(map.get("INSURANCE").toString()));
         	values.put("FINANCE", (double)values.get("FINANCE")+Double.parseDouble(map.get("FINANCE").toString()));
         	values.put("OLD_CAR", (double)values.get("OLD_CAR")+Double.parseDouble(map.get("OLD_CAR").toString()));
         	values.put("H_CUSTOMER", (double)values.get("H_CUSTOMER")+Double.parseDouble(map.get("H_CUSTOMER").toString()));
         	values.put("A_CUSTOMER", (double)values.get("A_CUSTOMER")+Double.parseDouble(map.get("A_CUSTOMER").toString()));
         	values.put("B_CUSTOMER", (double)values.get("B_CUSTOMER")+Double.parseDouble(map.get("B_CUSTOMER").toString()));
         	values.put("C_CUSTOMER", (double)values.get("C_CUSTOMER")+Double.parseDouble(map.get("C_CUSTOMER").toString()));
 			values.put("N_CUSTOMER", (double)values.get("N_CUSTOMER")+Double.parseDouble(map.get("N_CUSTOMER").toString()));
 			values.put("DCC_CUSTOMER", (double)values.get("DCC_CUSTOMER")+Double.parseDouble(map.get("DCC_CUSTOMER").toString()));
 			values.put("COMPLAIN_COUNT", (double)values.get("COMPLAIN_COUNT")+Double.parseDouble(map.get("COMPLAIN_COUNT").toString()));
 		}
         rows.add(values);
    	Map<String,List<Map>> excelData = new HashMap<String, List<Map>>();
    	excelData.put("销售业绩一览", resultList);
    	List<ExcelExportColumn> exportColmnList = new ArrayList<ExcelExportColumn>();
    	exportColmnList.add(new ExcelExportColumn("SOLD_BY","销售顾问"));
    	exportColmnList.add(new ExcelExportColumn("GOAL_SALES","目标"));
    	exportColmnList.add(new ExcelExportColumn("REAL_SALES","实际"));
    	exportColmnList.add(new ExcelExportColumn("RATE_SALES","完成率"));
    	exportColmnList.add(new ExcelExportColumn("JINGPIN_COUNT","台次"));
    	exportColmnList.add(new ExcelExportColumn("JINGPIN_AMOUNT","金额"));
    	exportColmnList.add(new ExcelExportColumn("JINGPIN_COST","成本"));
    	exportColmnList.add(new ExcelExportColumn("SALES_AMOUNT","销售额"));
    	exportColmnList.add(new ExcelExportColumn("INSURANCE","台次"));
    	exportColmnList.add(new ExcelExportColumn("FINANCE","台次"));
    	exportColmnList.add(new ExcelExportColumn("OLD_CAR","台次"));
      	exportColmnList.add(new ExcelExportColumn("H_CUSTOMER","H"));
    	exportColmnList.add(new ExcelExportColumn("JINGPIN_COST","成本"));
    	exportColmnList.add(new ExcelExportColumn("A_CUSTOMER","A"));
    	exportColmnList.add(new ExcelExportColumn("B_CUSTOMER","B"));
    	exportColmnList.add(new ExcelExportColumn("C_CUSTOMER","C"));
    	exportColmnList.add(new ExcelExportColumn("N_CUSTOMER","N"));
    	exportColmnList.add(new ExcelExportColumn("CREATE5","留档数"));
    	exportColmnList.add(new ExcelExportColumn("TEST","试乘驾数"));
    	exportColmnList.add(new ExcelExportColumn("BACK","多次进店邀回数"));
    	exportColmnList.add(new ExcelExportColumn("DEAL","成交数"));
    	exportColmnList.add(new ExcelExportColumn("TRANS","转介绍数"));
    	exportColmnList.add(new ExcelExportColumn("CREATE_RATE","留档率"));
    	exportColmnList.add(new ExcelExportColumn("TEST_RATE","试乘驾率"));
    	exportColmnList.add(new ExcelExportColumn("BACK_RATE","多次进店邀回率"));
    	exportColmnList.add(new ExcelExportColumn("DEAL_RATE","成交率"));
    	exportColmnList.add(new ExcelExportColumn("TRANS_RATE","转介绍占比"));
    	exportColmnList.add(new ExcelExportColumn("DCC_CUSTOMER","本月休眠客户数"));
    	exportColmnList.add(new ExcelExportColumn("COMPLAIN_COUNT","客诉数"));
    	//生成excel文件
    	excelService.generateExcelForDms(excelData, exportColmnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_销售业绩一览-按销售顾问.xls", request, response);
    	
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/series/excel", method = RequestMethod.GET)
    public void exportSeries(@RequestParam Map<String, String> queryParam, HttpServletRequest request, HttpServletResponse response ) {
    	List<Map> resultList = salesPerformanceservice.SalesPerformanceExprot(queryParam);
    	if(StringUtils.isNullOrEmpty(queryParam.get("userId"))){
    		queryParam.put("userId",resultList.get(0).get("USER_ID").toString());
    	}
    	List<Map> resultLists = salesPerformanceservice.SeriesExprot(queryParam);
    	 List<Map> row = resultLists;//获取当前页所有数据
         Map value = new HashMap();//留档
         value.put("SERIES_NAME", "总计");
         value.put("REAL_SALES", 0.00);
         value.put("JINGPIN_COUNT", 0.00);
         value.put("JINGPIN_AMOUNT", 0.00);
         value.put("JINGPIN_COST", 0.00);
         value.put("INSURANCE", 0.00);
         value.put("FINANCE", 0.00);
         value.put("OLD_CAR", 0.00);
         value.put("H_CUSTOMER", 0.00);
         value.put("A_CUSTOMER", 0.00);
         value.put("B_CUSTOMER", 0.00);
         value.put("C_CUSTOMER", 0.00);
         value.put("N_CUSTOMER", 0.00);
         value.put("CREATE5", "");
         value.put("TEST", ""); 
         value.put("BACK", "");
         value.put("DEAL", "");
         value.put("TRANS", "");
         value.put("CREATE_RATE", "");
         value.put("TEST_RATE","");
         value.put("BACK_RATE", "");
         value.put("DEAL_RATE", "");
         value.put("TRANS_RATE","");
         for (Map map : row) {
         	value.put("REAL_SALES", (double)value.get("REAL_SALES")+Double.parseDouble(map.get("REAL_SALES").toString()));
         	value.put("JINGPIN_COUNT", (double)value.get("JINGPIN_COUNT")+Double.parseDouble(map.get("JINGPIN_COUNT").toString()));
         	value.put("JINGPIN_AMOUNT", (double)value.get("JINGPIN_AMOUNT")+Double.parseDouble(map.get("JINGPIN_AMOUNT").toString()));
         	value.put("JINGPIN_COST", (double)value.get("JINGPIN_COST")+Double.parseDouble(map.get("JINGPIN_COST").toString()));
         	value.put("INSURANCE", (double)value.get("INSURANCE")+Double.parseDouble(map.get("INSURANCE").toString()));
         	value.put("FINANCE", (double)value.get("FINANCE")+Double.parseDouble(map.get("FINANCE").toString()));
         	value.put("OLD_CAR", (double)value.get("OLD_CAR")+Double.parseDouble(map.get("OLD_CAR").toString()));
         	value.put("H_CUSTOMER", (double)value.get("H_CUSTOMER")+Double.parseDouble(map.get("H_CUSTOMER").toString()));
         	value.put("A_CUSTOMER", (double)value.get("A_CUSTOMER")+Double.parseDouble(map.get("A_CUSTOMER").toString()));
         	value.put("B_CUSTOMER", (double)value.get("B_CUSTOMER")+Double.parseDouble(map.get("B_CUSTOMER").toString()));
         	value.put("C_CUSTOMER", (double)value.get("C_CUSTOMER")+Double.parseDouble(map.get("C_CUSTOMER").toString()));
 			value.put("N_CUSTOMER", (double)value.get("N_CUSTOMER")+Double.parseDouble(map.get("N_CUSTOMER").toString()));
 		}
         row.add(value);
   	Map<String,List<Map>> excelDatas = new HashMap<String, List<Map>>();
   	excelDatas.put("销售业绩一览", resultLists);
   	List<ExcelExportColumn> exportColmnLists = new ArrayList<ExcelExportColumn>();
   	exportColmnLists.add(new ExcelExportColumn("SERIES_NAME","车系"));
   	exportColmnLists.add(new ExcelExportColumn("REAL_SALES","实际"));
   	exportColmnLists.add(new ExcelExportColumn("JINGPIN_COUNT","台次"));
   	exportColmnLists.add(new ExcelExportColumn("JINGPIN_AMOUNT","金额"));
   	exportColmnLists.add(new ExcelExportColumn("JINGPIN_COST","成本"));
   	exportColmnLists.add(new ExcelExportColumn("INSURANCE","台次"));
   	exportColmnLists.add(new ExcelExportColumn("FINANCE","台次"));
   	exportColmnLists.add(new ExcelExportColumn("OLD_CAR","台次"));
   	exportColmnLists.add(new ExcelExportColumn("H_CUSTOMER","H"));
   	exportColmnLists.add(new ExcelExportColumn("A_CUSTOMER","A"));
   	exportColmnLists.add(new ExcelExportColumn("B_CUSTOMER","B"));
    exportColmnLists.add(new ExcelExportColumn("C_CUSTOMER","C"));
   	exportColmnLists.add(new ExcelExportColumn("N_CUSTOMER","N"));
   	exportColmnLists.add(new ExcelExportColumn("CREATE5","留档数"));
   	exportColmnLists.add(new ExcelExportColumn("TEST","试乘试驾数"));
   	exportColmnLists.add(new ExcelExportColumn("BACK","多次进店邀回数"));
   	exportColmnLists.add(new ExcelExportColumn("DEAL","成交数"));
   	exportColmnLists.add(new ExcelExportColumn("TRANS","转介绍数"));
   	exportColmnLists.add(new ExcelExportColumn("CREATE_RATE","留档率"));
   	exportColmnLists.add(new ExcelExportColumn("TEST_RATE","试乘试驾率"));
   	exportColmnLists.add(new ExcelExportColumn("BACK_RATE","多次进店邀回率"));
   	exportColmnLists.add(new ExcelExportColumn("DEAL_RATE","成交率"));
   	exportColmnLists.add(new ExcelExportColumn("TRANS_RATE","转介绍占比"));
   	//生成excel文件
   	excelService.generateExcelForDms(excelDatas, exportColmnLists, FrameworkUtil.getLoginInfo().getDealerShortName()+"_销售业绩一览-按车系.xls", request, response);
    }

}
