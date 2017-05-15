
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : OverFlowLossController.java
*
* @Author : DuPengXin
*
* @Date : 2016年10月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月11日    DuPengXin   1.0
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
import com.yonyou.dms.report.service.impl.OverFlowLossService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 报溢报损控制类
* @author DuPengXin
* @date 2016年10月11日
*/
@Controller
@TxnConn
@RequestMapping("/partReport/overflowloss")
@SuppressWarnings("rawtypes")
public class OverFlowLossController extends BaseController{

    @Autowired
    private OverFlowLossService oflservice;
    
    @Autowired
    private ExcelGenerator excelService;
    
    
    /**
    * 查询
    * @author DuPengXin
    * @date 2016年10月11日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryOverFlowLoss(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = oflservice.queryOverFlowLoss(queryParam);
        return pageInfoDto;
    }
    
    
    /**
    * 导出
    * @author DuPengXin
    * @date 2016年10月12日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
    */
    	
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportOverFlowLoss(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        List<Map> resultList = oflservice.queryOverFlowLossExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("报溢报损统计信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("STORAGE_NAME","仓库"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","库位"));
        exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
        exportColumnList.add(new ExcelExportColumn("STOCK_QUANTITY","库存数量"));
        exportColumnList.add(new ExcelExportColumn("price","成本单价"));
        exportColumnList.add(new ExcelExportColumn("LOSS_AMOUNT","盘亏金额"));
        //其它类型：Region_Provice,Region_City,Region_Country
        exportColumnList.add(new ExcelExportColumn("LOSS_QUANTITY","报损数量"));
        exportColumnList.add(new ExcelExportColumn("PROFIT_AMOUNT","报溢金额"));
        exportColumnList.add(new ExcelExportColumn("PROFIT_QUANTITY","报溢数量"));
        excelService.generateExcel(excelData, exportColumnList, "报溢报损统计信息.xls", request, response);

    }
}
