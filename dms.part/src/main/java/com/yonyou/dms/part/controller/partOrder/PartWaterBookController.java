
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartWaterBookController.java
*
* @Author : zhansw
*
* @Date : 2017年5月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月15日    zhansw    1.0
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
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.jsonSerializer.JSONUtil;
import com.yonyou.dms.part.service.partOrder.PartWaterBookService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 配件流水帐
* @author zhansw
* @date 2017年5月15日
*/
@Controller
@TxnConn
@RequestMapping("/partOrder/waterBook")
public class PartWaterBookController extends BaseController{

    @Autowired
    private PartWaterBookService partwaterbookservice;
    @Autowired
    private ExcelGenerator          excelService;
    
    
    /**
    * 业务描述: 查询符合条件的库存配件
    * @author zhansw
    * @date 2017年5月15日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    */
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartStockInfoUnifiedView(@RequestParam Map<String,String> queryParam) throws ServiceBizException{
        PageInfoDto id=partwaterbookservice.queryPartStockInfoUnifiedView(queryParam);
        return id;
        
    }
    @RequestMapping(value="/item",method=RequestMethod.GET)
    @ResponseBody
    public  PageInfoDto queryPartDetail(@RequestParam Map<String,String> queryParam) throws ServiceBizException{
        if(queryParam.get("scene")!=null&&!queryParam.get("scene").isEmpty()){
            Map resultList =JSONUtil.jsonToMap(queryParam.get("scene"));
            queryParam.putAll(resultList);
        }
       
        PageInfoDto id=partwaterbookservice.queryPartDetail(queryParam);
        return id;
    }
    
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportPartWaterBook(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        List<Map> resultList =partwaterbookservice.queryPartStockInfoUnifiedViewList(queryParam);
        Map<String, List<Map>> excelData = new HashMap<>();
        excelData.put("配件流水帐", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<>();
        exportColumnList.add(new ExcelExportColumn("dealer_shortname", "经销商简称"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_CODE_NAME", "仓库"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE", "库位"));
        exportColumnList.add(new ExcelExportColumn("PART_NO", "配件代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
        exportColumnList.add(new ExcelExportColumn("UNIT_CODE", "计量单位"));
        exportColumnList.add(new ExcelExportColumn("PART_MODEL_GROUP_CODE_SET", "配件车型组"));
        exportColumnList.add(new ExcelExportColumn("STOCK_QUANTITY", "库存数量"));
        exportColumnList.add(new ExcelExportColumn("BORROW_QUANTITY", "借进数量"));
        exportColumnList.add(new ExcelExportColumn("LEND_QUANTITY", "借出数量"));
        exportColumnList.add(new ExcelExportColumn("USEABLE_STOCK", "可用库存"));
        exportColumnList.add(new ExcelExportColumn("LAST_STOCK_IN", "最新入库日期"));
        exportColumnList.add(new ExcelExportColumn("LAST_STOCK_OUT", "最新出库日期"));


        excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_配件流水帐.xls", request, response);

    }
    @RequestMapping(value = "/export/excel/item", method = RequestMethod.GET)
    public void exportPartWaterBookItem(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        if(queryParam.get("scene")!=null&&!queryParam.get("scene").isEmpty()){
            Map resultList =JSONUtil.jsonToMap(queryParam.get("scene"));
            queryParam.putAll(resultList);
        }
        List<Map> resultList =partwaterbookservice.exportPartWaterBookItem(queryParam);
        Map<String, List<Map>> excelData = new HashMap<>();
        excelData.put("配件流水帐", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<>();
        exportColumnList.add(new ExcelExportColumn("OPERATE_DATE", "发生日期"));
        exportColumnList.add(new ExcelExportColumn("IN_OUT_TYPE", "进出类型",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("STOCK_IN_QUANTITY", "进数量"));
        exportColumnList.add(new ExcelExportColumn("STOCK_OUT_QUANTITY", "出数量"));
        exportColumnList.add(new ExcelExportColumn("IN_OUT_NET_PRICE", "出入库不含税单价"));
        exportColumnList.add(new ExcelExportColumn("IN_OUT_NET_AMOUNT", "出入库不含税金额"));
        exportColumnList.add(new ExcelExportColumn("IN_OUT_TAXED_PRICE", "出入库含税单价"));
        exportColumnList.add(new ExcelExportColumn("IN_OUT_TAXED_AMOUNT", "出入库含税金额"));
        exportColumnList.add(new ExcelExportColumn("STOCK_QUANTITY", "库存数量"));
        exportColumnList.add(new ExcelExportColumn("COST_PRICE", "成本单价"));
        exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
        exportColumnList.add(new ExcelExportColumn("SHEET_NO", "单据号码"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
        exportColumnList.add(new ExcelExportColumn("OPERATOR_NAME", "操作员"));


        excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_配件流水帐.xls", request, response);

    }
}
