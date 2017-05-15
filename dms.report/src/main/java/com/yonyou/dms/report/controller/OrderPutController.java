
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : OrderPutController.java
*
* @Author : zhongsw
*
* @Date : 2016年8月25日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月25日    zhongsw    1.0
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
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.report.service.impl.OrderPutService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 采购入库统计报表
* @author zhongsw
* @date 2016年8月25日
*/
@Controller
@TxnConn
@SuppressWarnings("rawtypes")
@RequestMapping("/report/OrderPut")
public class OrderPutController extends BaseController{
    
    @Autowired
    private OrderPutService orderPutService; 
    
    @Autowired
    private ExcelGenerator excelService;
    /**
    *  查询条件
    * @author zhongsw
    * @date 2016年8月25日
    * @param param
    * @return
    */
    @RequestMapping(method=RequestMethod.GET)
     @ResponseBody
     public PageInfoDto search(@RequestParam Map<String, String> param){
         return  orderPutService.searchOrderPut(param);
     }
    
    
    /**
    * 子表查询
    * @author zhongsw
    * @date 2016年8月25日
    * @param param
    * @return
    */
    	
    @RequestMapping(value="/{id}/item",method=RequestMethod.GET)
    @ResponseBody          
    public PageInfoDto search2(@PathVariable("id") Long id){
        return  orderPutService.searchOrderPut2(id);
    }
    
    /**
     * 根据查询条件返回采购入库统计主表数据
     * @author zhongsw
     * @date 2016年7月21日
     * @param queryParam 查询条件
     * @return 查询结果
     * @throws Exception
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportOverFlowLoss(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        List<Map> resultList = orderPutService.queryUsersForExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("采购入库统计信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_CODE","供应商代码"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME","供应商名称"));
        exportColumnList.add(new ExcelExportColumn("BEFORE_TAX_AMOUNT","含税总金额"));
        exportColumnList.add(new ExcelExportColumn("TOTAL_AMOUNT","总金额"));
        exportColumnList.add(new ExcelExportColumn("TAX_AMOUNT","税额"));
        excelService.generateExcel(excelData, exportColumnList, "采购入库统计信息.xls", request, response);

    }
    
    /**
     * 根据查询条件返回采购入库统计子表数据
     * @author zhongsw
     * @date 2016年7月21日
     * @param queryParam 查询条件
     * @return 查询结果
     * @throws Exception
     */
    @RequestMapping(value = "/export2", method = RequestMethod.GET)
    public void exportUsers2(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        List<Map> resultList = orderPutService.queryUsersForExport2(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("供应商统计明细信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("STOCK_IN_NO","入库单号"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_CODE","仓库"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","库位"));
        exportColumnList.add(new ExcelExportColumn("DELIVERY_NO","货运单号"));
        exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
        exportColumnList.add(new ExcelExportColumn("IN_QUANTITY","入库数量",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("IN_PRICE","不含税单价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("IN_AMOUNT","不含税金额",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("IN_PRICE_TAXED","含税单价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("IN_AMOUNT_TAXED","含税金额",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("PART_MAIN_TYPE","九大类"));
        exportColumnList.add(new ExcelExportColumn("FINISHED_DATE","完成/入账日期"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_CODE","供应商代码"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME","供应商名称"));
        exportColumnList.add(new ExcelExportColumn("FIT_MODEL_CODE","适用车型"));
        exportColumnList.add(new ExcelExportColumn("FROM_TYPE","来源方式"));
        exportColumnList.add(new ExcelExportColumn("CREATED_BY","经手人"));
//        String[] keys = { "PART_BUY_ID","STOCK_IN_NO","STORAGE_CODE","STORAGE_POSITION_CODE","DELIVERY_NO","PART_NO","PART_NAME","IN_QUANTITY","IN_PRICE","IN_AMOUNT","IN_PRICE_TAXED","IN_AMOUNT_TAXED","PART_MAIN_TYPE","FINISHED_DATE","CUSTOMER_CODE","CUSTOMER_NAME","FIT_MODEL_CODE","FROM_TYPE","CREATED_BY" };
//        String[] columnNames = { "序号","入库单号","仓库","库位","货运单号","配件代码","配件名称","入库数量","不含税单价","不含税金额","含税单价","含税金额","九大类","完成/入账日期","供应商代码","供应商名称","适用车型","来源方式","经手人"};
        //生成excel 文件
        excelService.generateExcel(excelData, exportColumnList, "采购入库子表信息.xls", request, response);

    }
}
