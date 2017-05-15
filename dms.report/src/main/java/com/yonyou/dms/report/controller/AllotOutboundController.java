
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : AllotOutboundController.java
*
* @Author : DuPengXin
*
* @Date : 2016年10月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月17日    DuPengXin   1.0
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
import com.yonyou.dms.report.service.impl.AllotOutboundService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 调拨出库统计控制类
* @author DuPengXin
* @date 2016年10月17日
*/
@Controller
@TxnConn
@RequestMapping("/partReport/allotStatistics/outbounds")
public class AllotOutboundController extends BaseController{
    @Autowired
    private AllotOutboundService aobservice;

    @Autowired
    private ExcelGenerator excelService;
    
    
    /**
    * 查询
    * @author DuPengXin
    * @date 2016年10月17日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryAllotOutbound(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = aobservice.queryAllotOutbound(queryParam);
        return pageInfoDto;
    }
    
    
    /**
    * 导出
    * @author DuPengXin
    * @date 2016年10月17日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
    */
    	
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportAllotOutbound(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        List<Map> resultList = aobservice.queryAllotOutboundExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("调拨出库统计信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("STORAGE_NAME","仓库"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","库位"));
        exportColumnList.add(new ExcelExportColumn("ALLOCATE_IN_NO","调拨出库单号"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_CODE","往来客户代码"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME","往来客户名称"));
        exportColumnList.add(new ExcelExportColumn("FINISHED_DATE","入帐日期"));
        exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
        exportColumnList.add(new ExcelExportColumn("OUT_PRICE","出库单价"));
        exportColumnList.add(new ExcelExportColumn("OUT_QUANTITY","出库数量"));
        exportColumnList.add(new ExcelExportColumn("OUT_AMOUNT","出库金额"));
        exportColumnList.add(new ExcelExportColumn("COST_PRICE","成本价"));
        exportColumnList.add(new ExcelExportColumn("COST_AMOUNT","成本金额"));
        excelService.generateExcel(excelData, exportColumnList, "调拨出库统计信息.xls", request, response);

    }
}
