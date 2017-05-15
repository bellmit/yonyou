
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : SalesTraceResultSearchContoller.java
*
* @Author : Administrator
*
* @Date : 2017年1月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月10日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.controller.customerManage;

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

import com.yonyou.dms.customer.service.customerManage.SalesTraceResultSearchService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* TODO description
* @author Administrator
* @date 2017年1月10日
*/

@Controller
@TxnConn
@RequestMapping("/customerManage/salesTraceResultSearch")
public class SalesTraceResultSearchContoller {
    @Autowired
    private SalesTraceResultSearchService salestraceresultsearchservice;
    
    @Autowired
    private ExcelGenerator excelService;
    
    /**
     * 销售回访结果查询
     * @param queryParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querySalesTraceResultInput(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salestraceresultsearchservice.querySalesTraceResultInput(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 潜客信息导出
     * 
     * @author LGQ
     * @date 2016年9月11日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportPotentialCus(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map> resultList = salestraceresultsearchservice.querySafeToExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("销售回访结果查询", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "客户编号"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_TYPE", "客户类型"));
        exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "意向品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "意向车系"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "意向车型"));
        exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
        exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NAME", "跟踪人"));
        exportColumnList.add(new ExcelExportColumn("SO_NO", "销售单号"));
        exportColumnList.add(new ExcelExportColumn("STOCK_OUT_DATE", "录入日期"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系人电话"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系人手机"));
        exportColumnList.add(new ExcelExportColumn("ZIP_CODE", "邮编"));
        exportColumnList.add(new ExcelExportColumn("E_MAIL", "邮箱"));
        exportColumnList.add(new ExcelExportColumn("PROVINCE", "省份", ExcelDataType.Dict));       
        exportColumnList.add(new ExcelExportColumn("CITY", "城市", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("DISTRICT", "县区", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("ADDRESS", "地址"));
        exportColumnList.add(new ExcelExportColumn("SOLD_NAME", "销售顾问"));
        exportColumnList.add(new ExcelExportColumn("QUESTIONNAIRE_NAME", "问卷名称"));
        
       
        exportColumnList.add(new ExcelExportColumn("SALES_DATE", "销售日期"));
        exportColumnList.add(new ExcelExportColumn("TASK_REMARK", "备注"));
        exportColumnList.add(new ExcelExportColumn("STOCK_OUT_DATE", "出库日期日期"));
        exportColumnList.add(new ExcelExportColumn("TRACE_STATUS", "跟踪状态"));       
       
        
        // 生成excel 文件
        excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_销售回访结果查询.xls", request, response);

    }
    

}
