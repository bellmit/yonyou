
/** 
*Copyright 2017 Yonyou Auto Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : ReleaseObsoleteDataQueryController.java
*
* @Author : Administrator
*
* @Date : 2017年4月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月19日    Administrator    1.0
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.part.service.basedata.ReleaseObsoleteDataQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * TODO description
 * 
 * @author Administrator
 * @date 2017年4月19日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/releaseObsoleteDataQuery")
public class ReleaseObsoleteDataQueryController {

    @Autowired
    public ReleaseObsoleteDataQueryService release;

    @Autowired
    private ExcelGenerator        excelService;
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryReleaseObsoleteDataQuery(@RequestParam Map<String, String> queryParams) {
        return release.queryReleaseObsoleteDataQuery(queryParams);
    }
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportReleaseObsoleteDataQuery(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        List<Map> resultList = release.exportReleaseObsoleteDataQuery(queryParam);
        Map<String, List<Map>> excelData = new HashMap<>();
        excelData.put("已发布呆滞品查询", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<>();
        
        // 生成excel 文件
        exportColumnList.add(new ExcelExportColumn("STORAGE_CODE_NAME", "仓库"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE", "库位代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NO", "配件代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
        exportColumnList.add(new ExcelExportColumn("UNIT_CODE", "计量单位"));
        exportColumnList.add(new ExcelExportColumn("CREATED_AT", "上报日期"));
        exportColumnList.add(new ExcelExportColumn("REPORTED_NUMBER", "上报数量"));
        exportColumnList.add(new ExcelExportColumn("REPORTED_PRICE", "上报单价"));
        exportColumnList.add(new ExcelExportColumn("REPORTED_TOTAL", "上报金额"));
        exportColumnList.add(new ExcelExportColumn("CONTACTS", "联系人"));
        exportColumnList.add(new ExcelExportColumn("PHONE", "联系电话"));
        exportColumnList.add(new ExcelExportColumn("ADDRESS", "联系地址"));
        exportColumnList.add(new ExcelExportColumn("REPORTED_COST_PRICE", "上报时成本单价"));
        exportColumnList.add(new ExcelExportColumn("REPORTED_SALE_PRICE", "上报时销售单价"));
        exportColumnList.add(new ExcelExportColumn("EXPIRATION_DATE", "呆滞品发布过期日期"));
        exportColumnList.add(new ExcelExportColumn("CANCEL_DATE", "取消日期"));
        exportColumnList.add(new ExcelExportColumn("SHELF_DATE", "下架日期"));
        
        
        excelService.generateExcelForDms(excelData, exportColumnList, "安徽复兴_已发布呆滞品查询.xls", request, response);
    }
    
    @RequestMapping(value = "/storageCode", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryStorageCode() {
        List<Map> map = release.queryStorageCode();
        return map;
    }
}
