
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : NewSalesOrderController.java
*
* @Author : zhanshiwei
*
* @Date : 2017年2月20日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月20日    zhanshiwei    1.0
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
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.report.service.impl.NewSalesOrderService;
import com.yonyou.dms.report.service.impl.RemainingOrderDetailService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * *新增订单简表
 * 
 * @author zhanshiwei
 * @date 2017年2月20日
 */
@Controller
@TxnConn
@RequestMapping("/report/newSalesOrder")
public class NewSalesOrderController extends BaseController {

    @Autowired
    private NewSalesOrderService        newsalesorderservice;

    @Autowired
    private RemainingOrderDetailService remainingOrderDetailService;
    @Autowired
    private ExcelGenerator              excelService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryNewSalesOrdre(@RequestParam Map<String, String> queryParam) {
        if (StringUtils.isEquals(queryParam.get("isCheck"), "50331001")) {
            return newsalesorderservice.queryNewSalesOrdreSeries(queryParam);
        } else {
            return newsalesorderservice.queryNewSalesOrdre(queryParam);
        }

    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map> resultList = newsalesorderservice.queryNewSalesOrdreList(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("车辆进厂月报", resultList);
        // 生成excel 文件
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("SOLD_BY", "销售顾问"));
        List<Map> listopetypeMap = remainingOrderDetailService.queryModel(queryParam);
        for (Map map : listopetypeMap) {
            exportColumnList.add(new ExcelExportColumn(map.get("CODE").toString(), map.get("NAME").toString()));
        }
        exportColumnList.add(new ExcelExportColumn("comm", "合计"));
        excelService.generateExcel(excelData, exportColumnList,
                                   FrameworkUtil.getLoginInfo().getDealerName() + "_留存订单简表.xls", request, response);
    }

}
