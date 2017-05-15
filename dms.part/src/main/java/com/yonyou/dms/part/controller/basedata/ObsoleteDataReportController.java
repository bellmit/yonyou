
/** 
*Copyright 2017 Yonyou Auto Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : ObsoleteDataReportController.java
*
* @Author : Administrator
*
* @Date : 2017年4月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月13日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.controller.basedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.part.domains.DTO.basedata.ObsoleteDataReportDTO;
import com.yonyou.dms.part.service.basedata.ObsoleteDataReportService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* 呆滞品数据上报
* @author Administrator
* @date 2017年4月13日
*/
@Controller
@TxnConn
@RequestMapping("/basedata/obsoleteDataReport")
public class ObsoleteDataReportController {
    @Autowired
    public ObsoleteDataReportService obsoleteDataReportService;
    @Autowired
    private ExcelGenerator excelService;
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryObsoleteDataReport(@RequestParam Map<String, String> queryParams){
        return obsoleteDataReportService.queryObsoleteDataReport(queryParams);
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void addObsoleteDataReport(@RequestBody @Valid ObsoleteDataReportDTO obsoleteDataReportDTO){
         obsoleteDataReportService.addObsoleteDataReport(obsoleteDataReportDTO);
    }
    
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryObsoleteDataReportById(@PathVariable String id){
        return obsoleteDataReportService.queryObsoleteDataReportById(id);
    }
    
    @RequestMapping(value = "/storageCode", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryStorageCode() {
        List<Map> map = obsoleteDataReportService.queryStorageCode();
        return map;
    }
    
    @RequestMapping(value = "/queryContacts", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> queryContacts() {
        return obsoleteDataReportService.queryContacts();
    }
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportObsoleteDataReport(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        List<Map> resultList = obsoleteDataReportService.exportObsoleteDataReport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<>();
        excelData.put("呆滞品数据上报", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<>();
        
        // 生成excel 文件
        exportColumnList.add(new ExcelExportColumn("STORAGE_CODE_NAME", "仓库"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE", "仓库代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NO", "配件代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
        exportColumnList.add(new ExcelExportColumn("STOCK_QUANTITY", "账面库存"));
        exportColumnList.add(new ExcelExportColumn("USEABLE_STOCK", "可用库存"));
        exportColumnList.add(new ExcelExportColumn("COST_PRICE", "成本单价"));
        exportColumnList.add(new ExcelExportColumn("SALES_PRICE", "销售单价"));
        exportColumnList.add(new ExcelExportColumn("COST_AMOUNT", "成本金额"));
        exportColumnList.add(new ExcelExportColumn("UNIT_CODE", "计量单位"));
        exportColumnList.add(new ExcelExportColumn("REPORTED_NUMBER", "已发布数量"));
        excelService.generateExcelForDms(excelData, exportColumnList, "安徽复兴_呆滞品数据上报.xls", request, response);
    }
}
