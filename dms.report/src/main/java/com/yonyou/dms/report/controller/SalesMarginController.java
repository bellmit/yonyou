
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : SalesMarginController.java
*
* @Author : zhanshiwei
*
* @Date : 2017年2月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月7日    zhanshiwei    1.0
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
import com.yonyou.dms.report.service.impl.SalesMarginService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 销售毛利
 * 
 * @author zhanshiwei
 * @date 2017年2月7日
 */
@Controller
@TxnConn
@RequestMapping("/financeCount/salesMargin")
public class SalesMarginController extends BaseController {

    @Autowired
    private SalesMarginService salesmarginservice;
    @Autowired
	 private ExcelGenerator excelService;
    
    /**
    *  销售毛利查询
    * @author zhanshiwei
    * @date 2017年2月7日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querySalesMargin(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesmarginservice.querySalesMargin(queryParam);
        return pageInfoDto;
    }
    
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/export/excel",method = RequestMethod.GET)
	public void excel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
	                  HttpServletResponse response){
	    List<Map> resultList =salesmarginservice.querySalesMarginExport(queryParam);
	    Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
	    excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_销售毛利统计", resultList);
	    List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
	    exportColumnList.add(new ExcelExportColumn("PRODUCT_CODE","物料代码"));
	    exportColumnList.add(new ExcelExportColumn("BRAND_NAME","品牌"));
	    exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型"));
	    exportColumnList.add(new ExcelExportColumn("CONFIG_NAME","配置"));
	    exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
	    exportColumnList.add(new ExcelExportColumn("VIN","VIN"));
	    exportColumnList.add(new ExcelExportColumn("PURCHASE_PRICE","采购价格"));
	    exportColumnList.add(new ExcelExportColumn("VEHCILE_REFERRENCE_PRICE","参考成本"));
	    exportColumnList.add(new ExcelExportColumn("VEHICLE_PRICE","销售价格"));
	    exportColumnList.add(new ExcelExportColumn("VEHICLE_SALES_PROFIT","车辆销售利润"));
	    exportColumnList.add(new ExcelExportColumn("PART_COST_AMOUNT","装潢成本"));
	    exportColumnList.add(new ExcelExportColumn("UPHOLSTER_SUM","装潢总价"));
	    exportColumnList.add(new ExcelExportColumn("PART_SALES_PROFIT","装潢利润"));
	    exportColumnList.add(new ExcelExportColumn("GARNITURE_COST_AMOUNT","精品总成本"));
	    exportColumnList.add(new ExcelExportColumn("GARNITURE_FREE_COST_AMOUNT","精品赠送成本"));
	    exportColumnList.add(new ExcelExportColumn("GARNITURE_SALES_COSE_AMOUNT","精品销售成本"));
	    exportColumnList.add(new ExcelExportColumn("GARNITURE_SUM","精品总价"));
	    exportColumnList.add(new ExcelExportColumn("GARNITURE_SALES_PROFIT","增值代办业务收入"));
	    exportColumnList.add(new ExcelExportColumn("RECEIVEABLE_AMOUNT","精品利润"));
	    exportColumnList.add(new ExcelExportColumn("ORDER_SALES_COST_AMOUNT","成本总额"));
	    exportColumnList.add(new ExcelExportColumn("ORDER_SALES_SUM","销售总额"));
	    exportColumnList.add(new ExcelExportColumn("ORDER_DERATED_SUM","减免总额"));
	    exportColumnList.add(new ExcelExportColumn("ORDER_SALES_PROFIT","利润总额"));
	    exportColumnList.add(new ExcelExportColumn("SO_NO","销售单据编号"));
	    exportColumnList.add(new ExcelExportColumn("USER_NAME","销售顾问"));
	    exportColumnList.add(new ExcelExportColumn("ORDER_SALES_DATE","开票日期",CommonConstants.FULL_DATE_TIME_FORMAT));
	    
	    
	    excelService.generateExcel(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_销售毛利统计.xls", request, response);
	}
}
