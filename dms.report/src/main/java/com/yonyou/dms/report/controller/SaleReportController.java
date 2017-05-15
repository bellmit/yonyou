
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : SaleReportController.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月27日    zhanshiwei    1.0
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
import com.yonyou.dms.report.service.impl.SaleReportService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 销售报表
 * 
 * @author zhanshiwei
 * @date 2016年9月27日
 */
@Controller
@TxnConn
@RequestMapping("/saleReport")
public class SaleReportController extends BaseController {

    @Autowired
    private SaleReportService salereportservice;
    @Autowired
    private ExcelGenerator  excelService;

    
    /**
    * 整车销售
    * @author zhanshiwei
    * @date 2016年9月27日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(value = "/vehicleSales", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryVehicleSales(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salereportservice.queryVehicleSales(queryParam);
        return pageInfoDto;
    }
    
    /**
    * 销售统计分析
    * @author zhanshiwei
    * @date 2016年9月28日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(value = "/salesAnalysis", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querySaleStatistics(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salereportservice.querySaleStatistics(queryParam);
        return pageInfoDto;
    }
    
    /**
    * 销售统计分析导出
    * @author zhanshiwei
    * @date 2016年11月30日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
    */
    	
    @RequestMapping(value="/salesAnalysis/export",method=RequestMethod.GET)
    public void exportSaleStatistics(@RequestParam Map<String,String> queryParam,HttpServletRequest request,HttpServletResponse response)throws Exception {
        List<Map> resultList = salereportservice.exportSaleStatistics(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("销售统计分析", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("CUS_SOURCE", "客户来源",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("VISITING_NUM", "展厅接待人数"));
        exportColumnList.add(new ExcelExportColumn("POTENTIAL_NUM", "客户建档人数"));
        exportColumnList.add(new ExcelExportColumn("SALE_NUM", "新增订单数"));
        exportColumnList.add(new ExcelExportColumn("NEWCAR_NUM", "成交车辆数"));
        exportColumnList.add(new ExcelExportColumn("POTENTIAL_PERCENT", "建档率"));
        exportColumnList.add(new ExcelExportColumn("CONTRACT_PERCENT", "签约率"));
        exportColumnList.add(new ExcelExportColumn("DEAL_PERCENT", "成交率"));


        excelService.generateExcel(excelData, exportColumnList, "销售统计分析.xls", request, response);
    }
    /**
    * 销售顾问业绩统计
    * @author zhanshiwei
    * @date 2016年9月28日
    * @param queryParam
    * @return
    */
        
    @RequestMapping(value = "/consultantDeeds", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryconsultantDeeds(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salereportservice.queryconsultantDeeds(queryParam);
        return pageInfoDto;
    }
    
    /**
    * 销售顾问业绩统计导出
    * @author zhanshiwei
    * @date 2016年12月5日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
    */
    	
    @RequestMapping(value="/consultantDeeds/export",method=RequestMethod.GET)
    public void exporconsultantDeeds(@RequestParam Map<String,String> queryParam,HttpServletRequest request,HttpServletResponse response)throws Exception {
        List<Map> resultList = salereportservice.exporconsultantDeeds(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("销售顾问业绩统计", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("ORG_SHORT_NAME", "部门"));
        exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NAME", "销售顾问"));
        exportColumnList.add(new ExcelExportColumn("RESIDUE_ORDER_NUM", "剩余订单数"));
        exportColumnList.add(new ExcelExportColumn("VALID_ORDER_NUM", "车辆发生数"));
        exportColumnList.add(new ExcelExportColumn("INVOICE_NUM", "开票数"));
        exportColumnList.add(new ExcelExportColumn("ORDER_SUM", "车辆销售总额"));
        exportColumnList.add(new ExcelExportColumn("AFTER_DISCOUNT_AMOUNT", "装潢工时总额"));
        exportColumnList.add(new ExcelExportColumn("PRAFTER_DISCOUNT_AMOUNT", "装潢配件总额"));
        exportColumnList.add(new ExcelExportColumn("SERVICE_SUM", "服务项目总额"));
        exportColumnList.add(new ExcelExportColumn("ORDER_SUM", "订单总金额"));

        excelService.generateExcel(excelData, exportColumnList, "销售顾问业绩统计.xls", request, response);

    }
    
    /**
    * 车辆进销存
    * @author zhanshiwei
    * @date 2016年9月29日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(value = "/vehicleStock", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryVehicleStock(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salereportservice.queryVehicleStock(queryParam);
        return pageInfoDto;
    }
    
    /**
    * 车辆进销存导出
    * @author zhanshiwei
    * @date 2016年11月30日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
    */
    	
    @RequestMapping(value="/vehicleStock/export",method=RequestMethod.GET)
    public void exportVehicleStock(@RequestParam Map<String,String> queryParam,HttpServletRequest request,HttpServletResponse response)throws Exception {
        List<Map> resultList = salereportservice.exportVehicleStock(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("车辆进销存", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
        exportColumnList.add(new ExcelExportColumn("CONFIG_NAME", "配置"));
        exportColumnList.add(new ExcelExportColumn("COLOR", "颜色"));
        exportColumnList.add(new ExcelExportColumn("EXIST_NUM", "当前库存"));
        exportColumnList.add(new ExcelExportColumn("MONTH_NUM", "月初库存"));
        exportColumnList.add(new ExcelExportColumn("IN_NUM", "当月购入"));
        exportColumnList.add(new ExcelExportColumn("OUT_NUM", "当月销售"));
        exportColumnList.add(new ExcelExportColumn("EXIST_NUM", "月末库存"));
        excelService.generateExcel(excelData, exportColumnList, "车辆进销存.xls", request, response);
    }
    
    
    /**
    * 整车销售报表导出
    * @author zhanshiwei
    * @date 2016年11月30日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
    */
    @RequestMapping(value="/vehicleSales/export",method=RequestMethod.GET)
    public void exportVehicleSales(@RequestParam Map<String, String> queryParam , HttpServletRequest request,  HttpServletResponse response) throws Exception {
        List<Map> resultList = salereportservice.exportVehicleSales(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("销售明细", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("SO_NO", "订单编码"));
        exportColumnList.add(new ExcelExportColumn("LATEST_STOCK_OUT_DATE", "出库日期"));
        exportColumnList.add(new ExcelExportColumn("ORG_SHORT_NAME", "部门"));
        exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NAME", "服务顾问"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系电话"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_ADDRESS", "联系地址"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_ZIP_CODE", "邮编"));
        exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
        exportColumnList.add(new ExcelExportColumn("VIN", "VIN号"));
        exportColumnList.add(new ExcelExportColumn("ENGINE_NO", "发动机号"));
        exportColumnList.add(new ExcelExportColumn("PAY_TYPE_CODE", "付款方式"));
        exportColumnList.add(new ExcelExportColumn("VEHICLE_PRICE", "车辆销售价"));
        exportColumnList.add(new ExcelExportColumn("DIRECTIVE_PRICE", "销售指导价"));
        exportColumnList.add(new ExcelExportColumn("PURCHASE_PRICE", "车辆采购价"));
        exportColumnList.add(new ExcelExportColumn("PRIVILEGE_PRICE", "车款优惠"));
        exportColumnList.add(new ExcelExportColumn("INVOICE_DATE", "开票日期"));
        exportColumnList.add(new ExcelExportColumn("VEHICLE_PRICE", "开票价格"));
        exportColumnList.add(new ExcelExportColumn("PROFIT_PRICE", "销售毛利"));
        exportColumnList.add(new ExcelExportColumn("PROFITRATE_PRICEPROFITRATE_PRICE", "销售毛利率"));
        exportColumnList.add(new ExcelExportColumn("BUSINESS_TYPE", "订单类型", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("CONFIG_NAME", "配置"));
        exportColumnList.add(new ExcelExportColumn("INVOICE_DATE", "入库日期"));
        
        excelService.generateExcel(excelData, exportColumnList, "销售明细.xls", request, response);
    }
}
