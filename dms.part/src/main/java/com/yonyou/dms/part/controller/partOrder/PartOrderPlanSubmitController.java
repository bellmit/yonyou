
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartOrderPlanSubmitController.java
*
* @Author : zhanshiwei
*
* @Date : 2017年5月3日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月3日    zhanshiwei    1.0
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPtDmsOrderDTO;
import com.yonyou.dms.part.service.partOrder.PartOrderPlanSubmitService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 配件订货上报
 * 
 * @author zhanshiwei
 * @date 2017年5月3日
 */
@Controller
@TxnConn
@RequestMapping("/partOrder/partSubmit")
public class PartOrderPlanSubmitController extends BaseController {

    @Autowired
    private PartOrderPlanSubmitService partorderplansubmit;
    @Autowired
    private ExcelGenerator             excelService;


    /**
     * 描述：配件订单查询，根据本地订单号
     * 
     * @author zhanshiwei
     * @date 2017年5月3日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDmsPtOrderInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto id = partorderplansubmit.queryDmsPtOrderInfo(queryParam);
       // HttpClientUtil.httpGetRequest("http://localhost:8080/dms.web/part/rest/partOrder/OrderPlanQuery");
        return id;
    }

    /**
     * 修改
     * 
     * @author zhanshiwei
     * @date 2017年5月3日
     * @param orderNo
     * @return
     */

    @RequestMapping(value = "/{orderNo}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> modifyDmsPtOrderInfo(@PathVariable("orderNo") String orderNo) {
        Map<String, Object> id = partorderplansubmit.modifyDmsPtOrderInfo(orderNo);
        return id;
    }

    /**
     * 业务描述：对订单备注的更新操作
     * 
     * @author zhanshiwei
     * @date 2017年5月3日
     * @param orderNo
     * @param ttPtOrderIntenDto
     * @param uriCB
     */

    @RequestMapping(value = "/{orderNo}/updateDmsPtOrderRemark", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void modifyUpdateDmsPtOrderRemark(@PathVariable String orderNo, @RequestBody TtPtDmsOrderDTO ttPtOrderDto,
                                             UriComponentsBuilder uriCB) {
        partorderplansubmit.modifyUpdateDmsPtOrderRemark(orderNo, ttPtOrderDto);
    }

    /**
     * 业务描述：查询订单明细
     * 
     * @author zhanshiwei
     * @date 2017年5月3日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/QueryDmsPtOrderItemInfo", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDmsPtOrderItemInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto id = partorderplansubmit.queryDmsPtOrderItemInfo(queryParam);
        return id;

    }

    /**
     * @author zhanshiwei
     * @date 2017年5月3日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void queryDmsPtOrderItemInfoExport(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {
        List<Map> resultList = partorderplansubmit.queryDmsPtOrderItemInfoExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<>();
        excelData.put("_订单明细", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<>();
        exportColumnList.add(new ExcelExportColumn("PART_NO", "配件代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
        exportColumnList.add(new ExcelExportColumn("COUNT", "订货数量"));
        exportColumnList.add(new ExcelExportColumn("NETWR", "含税单价"));
        exportColumnList.add(new ExcelExportColumn("TOTAL", "含税金额"));
        exportColumnList.add(new ExcelExportColumn("OPNETWR", "不含税单价"));
        exportColumnList.add(new ExcelExportColumn("OPNETWR", "不含税总额"));
        exportColumnList.add(new ExcelExportColumn("NO_TAX_PRICE", "不含税终端销售价"));
        exportColumnList.add(new ExcelExportColumn("SINGLE_Discount", "单个折扣"));
        exportColumnList.add(new ExcelExportColumn("UNIT_CODE", "单位"));
        exportColumnList.add(new ExcelExportColumn("DETAIL_REMARK", "备注"));
        exportColumnList.add(new ExcelExportColumn("IS_MOP_ORDER", "是否M", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("IS_SJV", "是否S", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("REPORT_WAY", "提报方式", ExcelDataType.Dict));
        excelService.generateExcel(excelData, exportColumnList, "_订单明细.xls", request, response);
    }

    /**
     * @author zhanshiwei
     * @date 2017年5月4日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/export/moexcel", method = RequestMethod.GET)
    public void queryMoDmsPtOrderItemInfoExport(@RequestParam Map<String, String> queryParam,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        queryParam.put("IS_SJV", DictCodeConstants.DICT_IS_NO);
        queryParam.put("IS_MOP", DictCodeConstants.DICT_IS_YES);
        List<Map> resultList = partorderplansubmit.queryMoDmsPtOrderItemInfoExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<>();
        excelData.put("_订单明细", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<>();
        exportColumnList.add(new ExcelExportColumn("PART_NO", "FM"));
        exportColumnList.add(new ExcelExportColumn("COUNT", "订货数量"));

        excelService.generateExcel(excelData, exportColumnList, "_订单明细.xls", request, response);
    }

    /**
     * 业务描述：更新配件主表完成状态
     * 
     * @author zhanshiwei
     * @date 2017年5月3日
     * @param ttPtOrderDto
     * @param uriCB
     */

    @RequestMapping(value = "/MaintainDmsPtOrderAchieve", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void MaintainDmsPtOrderAchieve(@RequestBody TtPtDmsOrderDTO ttPtOrderDto, UriComponentsBuilder uriCB) {

        partorderplansubmit.MaintainDmsPtOrderAchieve(ttPtOrderDto);
    }

    /**
     * 业务描述：配件计划作废功能
     * 
     * @author zhanshiwei
     * @date 2017年5月4日
     * @param ttPtOrderDto
     * @param uriCB
     */

    @RequestMapping(value = "/deleteDmsPtOrderPlan", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteDmsPtOrderPlan(@RequestBody TtPtDmsOrderDTO ttPtOrderDto, UriComponentsBuilder uriCB) {
        partorderplansubmit.deleteDmsPtOrderPlan(ttPtOrderDto);
    }
    
    @RequestMapping(value = "/SEDMSP01", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void SEDMSP01(@RequestBody TtPtDmsOrderDTO ttPtOrderDto, UriComponentsBuilder uriCB) {
        
    }

}
