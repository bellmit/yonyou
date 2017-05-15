
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : InsideConsumingController.java
*
* @Author : DuPengXin
*
* @Date : 2016年10月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月12日    DuPengXin   1.0
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
import com.yonyou.dms.report.service.impl.InsideConsumingService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 内部领用统计控制类
* @author DuPengXin
* @date 2016年10月12日
*/
@Controller
@TxnConn
@SuppressWarnings("rawtypes")
@RequestMapping("/partReport/insideConsumings")
public class InsideConsumingController extends BaseController {

    @Autowired
    private InsideConsumingService icService;
    
    @Autowired
    private ExcelGenerator excelService;
    
    
    /**
    * 查询
    * @author DuPengXin
    * @date 2016年10月12日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryInsideConsuming(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = icService.queryInsideConsuming(queryParam);
        return pageInfoDto;
    }
    
    
    /**
    * 查询内部领用统计明细
    * @author DuPengXin
    * @date 2016年10月12日
    * @param id
    * @return
    */
    	
    @RequestMapping(value = "/{id}/Items",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getInsideConsumingItems(@PathVariable(value = "id") Long id) {
        List<Map> list = icService.getInsideConsumingItems(id);
        return list;
    }
    
    
    /**
    * 内部领用统计信息导出
    * @author DuPengXin
    * @date 2016年10月13日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
    */
    	
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportInsideConsuming(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        List<Map> resultList = icService.queryInsideConsumingExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("内部领用统计信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("ORG_NAME","领用部门"));
        exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NAME","领用人"));
        exportColumnList.add(new ExcelExportColumn("COST_AMOUNT","成本金额"));
        excelService.generateExcel(excelData, exportColumnList, "内部领用统计信息.xls", request, response);
    }
    
    /**
    * 导出内部领用统计信息明细
    * @author DuPengXin
    * @date 2016年10月13日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
    */
    	
    @RequestMapping(value = "/{id}/export/Items", method = RequestMethod.GET)
    public void exportInsideConsumingItem(@PathVariable Long id, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        List<Map> resultList = icService.queryInsideConsumingExportItems(id);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("内部领用统计明细信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("RECEIPT_NO","领用单号"));
        exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NAME","领用人"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_CODE","仓库"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","库位"));
        exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
        exportColumnList.add(new ExcelExportColumn("OUT_QUANTITY","出库数量"));
        //其它类型：Region_Provice,Region_City,Region_Country
        exportColumnList.add(new ExcelExportColumn("COST_PRICE","成本单价"));
        exportColumnList.add(new ExcelExportColumn("COST_AMOUNT","成本金额"));
        excelService.generateExcel(excelData, exportColumnList, "内部领用统计明细信息.xls", request, response);
    }
}
