
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : ManageVerifyController.java
*
* @Author : xukl
*
* @Date : 2016年9月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月28日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.controller.ordermanage;


import java.util.List;
import java.util.Map;

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
import com.yonyou.dms.retail.domains.DTO.ordermanage.SoAuditingDTO;
import com.yonyou.dms.retail.service.ordermanage.ManageVerifyService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 销售单审核控制类
* @author LGq
* @date 2016年9月28日
*/
@Controller
@TxnConn
@RequestMapping("/ordermanage/manageVerify")
public class ManageVerifyController extends BaseController{
    @Autowired
    private ManageVerifyService manageVerifyService;
    
    /**
     * 销售订单查询
     * @author LGQ
     * @date 2017年1月13日
     * @param queryParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qrySalesOrders(@RequestParam Map<String, String> queryParam) {
        System.out.println("1");
        PageInfoDto pageInfoDto = manageVerifyService.querySalesOrdersAudit(queryParam);
        System.out.println("2");
        return pageInfoDto;
    }
    
    /**
     * 记录经理审核
     */
    @RequestMapping(value = "/audit/auditList", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void auditSalesOrder(@RequestBody SoAuditingDTO soAuditingDTO,
                                           UriComponentsBuilder uriCB) {
        manageVerifyService.auditSalesOrder(soAuditingDTO);   
    }
    
    /**
     * 审批记录
     * 
     * @author LGQ
     * @date 2016年1月1日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/search/auditDetail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryAudtiDetail(@PathVariable(value = "id") String id) {
        PageInfoDto pageInfoDto = manageVerifyService.queryAudtiDetail(id); 
       
        return pageInfoDto;
    }
    
    /**
     * 审批记录
     * 
     * @author LGQ
     * @date 2016年1月1日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/manageAudit/{id}/{by}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map>  manageAudit(@PathVariable(value = "id") String id,@PathVariable(value = "by") String by) {
        List<Map> result  = manageVerifyService.manageAudit(id,by); 
        return result;
    }
    
    /**
     * 审批记录
     * 
     * @author LGQ
     * @date 2016年1月1日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/commitAudit/{id}/{by}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> commidAudit(@PathVariable(value = "id") String id,@PathVariable(value = "by") String by) {
        System.out.println("111111111111111111111");
        System.out.println(id);
        List<Map> result  = manageVerifyService.commidAudit(id,by); 
        return result;
    }
    /**
     * 提交审核
     */
    @RequestMapping(value = "/commit/commitAudit", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCommitAudit(@RequestBody SoAuditingDTO soAuditingDTO,
                                           UriComponentsBuilder uriCB) {
        manageVerifyService.saveCommitAudit(soAuditingDTO);   
    }
    
   
}
