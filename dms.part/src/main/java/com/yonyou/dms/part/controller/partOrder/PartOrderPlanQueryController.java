
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartOrderPlanQueryController.java
*
* @Author : zhanshiwei
*
* @Date : 2017年5月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月5日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.controller.partOrder;

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
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.part.service.partOrder.PartOrderPlanQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 配件订货计划查询
 * 
 * @author zhanshiwei
 * @date 2017年5月5日
 */
@Controller
@TxnConn
@RequestMapping("/partOrder/OrderPlanQuery")
public class PartOrderPlanQueryController extends BaseController {

    @Autowired
    private PartOrderPlanQueryService partqueryService;
    @Autowired
    private ExcelGenerator            excelService;

    /**
     * 业务描述：配件订货计划查询
     * 
     * @author zhanshiwei
     * @date 2017年5月5日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDmsPtOrderPlan(@RequestParam Map<String, String> queryParam) {
        PageInfoDto id = partqueryService.queryDmsPtOrderPlan(queryParam);
        return id;
    }

    /**
     * 导出
     * 
     * @author zhanshiwei
     * @date 2017年5月6日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void queryDmsPtOrderPlanExport(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        List<Map> resultList = partqueryService.queryDmsPtOrderPlanExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<>();
        excelData.put("配件订单状态查询", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<>();
        exportColumnList.add(new ExcelExportColumn("OEM_TAG", "OEM标志", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("ORDER_NO", "订单编号"));
        exportColumnList.add(new ExcelExportColumn("SAP_ORDER_NO", "SAP订单号"));
        exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
        exportColumnList.add(new ExcelExportColumn("SEND_SAP_DATE", "上报SAP时间",CommonConstants.FULL_DATE_TIME_FORMAT));
        exportColumnList.add(new ExcelExportColumn("PART_ORDER_TYPE", "订单类型", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("TOTAL", "含税总金额"));
        exportColumnList.add(new ExcelExportColumn("GKFWB_DATE", "需求日期",CommonConstants.FULL_DATE_TIME_FORMAT));
        exportColumnList.add(new ExcelExportColumn("ORDER_DATE", "订货日期",CommonConstants.FULL_DATE_TIME_FORMAT));
        exportColumnList.add(new ExcelExportColumn("MAIN_ORDER_TYPE", "订单分类"));
        exportColumnList.add(new ExcelExportColumn("SUBMIT_TIME", "上报日期",CommonConstants.FULL_DATE_TIME_FORMAT));
        exportColumnList.add(new ExcelExportColumn("ORDER_STATUS", "订单上报状态"));
        exportColumnList.add(new ExcelExportColumn("IS_ACHIEVE", "是否完成", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("IS_VALID", "是否有效", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
        exportColumnList.add(new ExcelExportColumn("IS_MOP_ORDER", "是否MOP拆单", ExcelDataType.Dict));

        excelService.generateExcel(excelData, exportColumnList, "配件订单状态查询.xls", request, response);
    }
}
