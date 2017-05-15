
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : PartSellController.java
*
* @Author : zhongsw
*
* @Date : 2016年9月1日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月1日    zhongsw    1.0
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
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.report.service.impl.PartSellService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 配件销售统计报表
* @author zhongsw
* @date 2016年9月1日
*/
@Controller
@TxnConn
@RequestMapping("/report/partSell")
public class PartSellController extends BaseController{
    
    @Autowired
    private PartSellService partSellService; 
    
    @Autowired
    private ExcelGenerator excelService;
    
    
    /**查询
    * TODO description
    * @author zhongsw
    * @date 2016年9月1日
    * @param param
    * @return
    */
    	
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto search(@RequestParam Map<String, String> param){
        return  partSellService.searchOrderPut(param);
    }
    
    /**导出
    * TODO description
    * @author zhongsw
    * @date 2016年9月1日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
    */
    	
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportUsers(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        List<Map> resultList = partSellService.queryUsersForExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("配件销售统计", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("STORAGE_CODE","仓库代码"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","库位代码"));
        exportColumnList.add(new ExcelExportColumn("SALES_PART_NO","配件销售单号"));
        exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("PART_SALES_PRICE","配件销售单价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("PART_QUANTITY","配件数量",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("PART_SALES_AMOUNT","配件销售金额",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("DISCOUNT","折扣率",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("SALES_AMOUNT","配件收费金额"));
        exportColumnList.add(new ExcelExportColumn("COST_PRICE","配件成本单价"));
        exportColumnList.add(new ExcelExportColumn("GROSS_PROFIT","毛利(不含税)"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME","客户名称"));
        exportColumnList.add(new ExcelExportColumn("RO_NO","工单号",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("PART_MAIN_TYPE","九大类",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("CREATED_BY","发料人",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("FINISHED_DATE","入账日期",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("FINISHED_DATE","发料时间",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("RO_STATUS","工单状态",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("FINISHED_DATE","结算日期"));
        exportColumnList.add(new ExcelExportColumn("FINISHED_DATE","结清日期",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("REMARK","备注",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("CREATED_BY","销售顾问"));
        String[] keys = { "STORAGE_CODE","STORAGE_POSITION_CODE","SALES_PART_NO","PART_NO","PART_NAME","PART_SALES_PRICE","PART_QUANTITY","PART_SALES_AMOUNT","DISCOUNT","SALES_AMOUNT","COST_PRICE","GROSS_PROFIT","CUSTOMER_NAME","RO_NO","PART_MAIN_TYPE","CREATED_BY","FINISHED_DATE","FINISHED_DATE","RO_STATUS","FINISHED_DATE","FINISHED_DATE","REMARK","CREATED_BY"};
        String[] columnNames = { "仓库代码","库位代码","配件销售单号","配件代码","配件名称","配件销售单价","配件数量","配件销售金额","折扣率","配件收费金额","配件成本单价","毛利(不含税)","客户名称","工单号","九大类","发料人","入账日期","发料时间","工单状态","结算日期","结清日期","备注","销售顾问" };
        excelService.generateExcel(excelData, exportColumnList, "配件销售统计.xls", request, response);
    }

}
