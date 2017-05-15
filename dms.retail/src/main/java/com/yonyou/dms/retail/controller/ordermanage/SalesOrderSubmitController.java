
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SalesOrderSubmitController.java
*
* @Author : Administrator
*
* @Date : 2017年2月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月14日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.controller.ordermanage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.retail.domains.DTO.ordermanage.FinanceOrderDTO;
import com.yonyou.dms.retail.domains.DTO.ordermanage.FinanceShutOrderDTO;
import com.yonyou.dms.retail.service.ordermanage.SalesOrderSubmitService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* TODO description
* @author Administrator
* @date 2017年2月14日
*/
@Controller
@TxnConn
@SuppressWarnings("rawtypes")
@RequestMapping("/ordermanage/submit")
public class SalesOrderSubmitController extends BaseController{
    @Autowired
    private SalesOrderSubmitService salesOrderSubmitService;
    @Autowired
    private CommonNoService  commonNoService;
    @Autowired
    private SystemParamService  systemparamservice;
    /**
     * 财务经理审核查询
     * @author xukl
     * @date 2016年9月8日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/FinanceVerify/query" ,method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qrySalesOrdersForFinVrfy(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderSubmitService.qrySRSForFincAudit(queryParam);
        return pageInfoDto;
    }
    
    @RequestMapping(value = "/audit/auditList", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void auditSalesOrder(@RequestBody FinanceOrderDTO financeDTO,
                                           UriComponentsBuilder uriCB) {
        salesOrderSubmitService.auditSalesOrder(financeDTO);   
    }
    @RequestMapping(value = "/financeverify/shut", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void auditSalesOrderShut(@RequestBody FinanceShutOrderDTO financeShutDTO,
                                           UriComponentsBuilder uriCB) {
        salesOrderSubmitService.auditSalesOrderShut(financeShutDTO);   
    }
    
    @RequestMapping(value = "/financeverify/money", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void auditSalesOrderMoney(@RequestBody FinanceShutOrderDTO financeShutDTO,
                                           UriComponentsBuilder uriCB) {
        salesOrderSubmitService.auditSalesOrderMoney(financeShutDTO);   
    }
}
