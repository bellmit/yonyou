
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : PartFlowController.java
*
* @Author : DuPengXin
*
* @Date : 2016年8月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月22日    DuPengXin   1.0
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
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.report.service.impl.PartFlowService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 配件流水账查询操作类
* @author DuPengXin
* @date 2016年8月22日
*/
@Controller
@TxnConn
@SuppressWarnings("rawtypes")
@RequestMapping("/partReport/partflows")
public class PartFlowController extends BaseController {
    @Autowired
    private PartFlowService partFlowService;
    
    @Autowired
    private ExcelGenerator excelService;
    
    /**
    * 查询
    * @author DuPengXin
    * @date 2016年8月22日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartflows(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = partFlowService.queryPartflows(queryParam);
        return pageInfoDto;
    }
    
    
    /**
    * 根据ID查询
    * @author DuPengXin
    * @date 2016年10月25日
    * @param id
    * @return
    */
    	
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Map queryPartflowsById(@PathVariable(value = "id") Long id) {
        return partFlowService.queryPartflowsById(id);
    }
    
    /**
    * 配件流水明细
    * @author DuPengXin
    * @date 2016年8月26日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(value="/{STORAGE_CODE}/items",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartdetails(@PathVariable(value = "STORAGE_CODE") String storageCode,@RequestParam Map<String, String> queryParam) {
       
        PageInfoDto pageInfoDto = partFlowService.queryPartdetails(queryParam,storageCode);
        return pageInfoDto;
    }  
    
    
    /**
    * 导出配件流水账明细
    * @author DuPengXin
    * @date 2016年8月29日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
    */
    	
    @RequestMapping(value = "/{STORAGE_CODE}/export", method = RequestMethod.GET)
    public void exportPartInfos(@PathVariable(value = "STORAGE_CODE") String storageCode,@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        List<Map> resultList = partFlowService.queryPartdetailExport(queryParam,storageCode);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("配件流水账明细信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("OPERATE_DATE","发生日期",CommonConstants.FULL_DATE_TIME_FORMAT));
        exportColumnList.add(new ExcelExportColumn("IN_OUT_TYPE","出入库类型",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("SHEET_NO","出入库单号"));
        exportColumnList.add(new ExcelExportColumn("STOCK_IN_QUANTITY","入库数量"));
        exportColumnList.add(new ExcelExportColumn("STOCK_OUT_QUANTITY","出库数量"));
        exportColumnList.add(new ExcelExportColumn("STOCK_QUANTITY","库存数量"));
        exportColumnList.add(new ExcelExportColumn("COST_PRICE","成本单价"));
        //其它类型：Region_Provice,Region_City,Region_Country
        exportColumnList.add(new ExcelExportColumn("COST_AMOUNT","成本金额"));
        exportColumnList.add(new ExcelExportColumn("IN_OUT_NET_PRICE","出入库不含税单价"));
        exportColumnList.add(new ExcelExportColumn("IN_OUT_NET_AMOUNT","出入库不含税金额"));
        exportColumnList.add(new ExcelExportColumn("IN_OUT_TAXED_PRICE","出入库含税单价"));
        exportColumnList.add(new ExcelExportColumn("IN_OUT_TAXED_AMOUNT","出入库含税金额"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_CODE","客户代码"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME","客户名称"));
        exportColumnList.add(new ExcelExportColumn("OPERATOR","操作员"));
        excelService.generateExcel(excelData, exportColumnList, "配件流水账明细信息.xls", request, response);

    }
}
