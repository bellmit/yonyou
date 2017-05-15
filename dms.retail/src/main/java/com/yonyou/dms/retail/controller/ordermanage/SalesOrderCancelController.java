
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SalesOrderCancelController.java
*
* @Author : LiGaoqi
*
* @Date : 2017年1月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月13日    LiGaoqi    1.0
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

import com.yonyou.dms.common.domains.DTO.basedata.SalesOrderCancelDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.retail.service.ordermanage.SalesOrderCancelService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;


/**
* TODO description
* @author LiGaoqi
* @date 2017年1月13日
*/
@Controller
@TxnConn
@RequestMapping("/ordermanage/salesOrderCancel")
public class SalesOrderCancelController extends BaseController {
    @Autowired
    private SalesOrderCancelService salesOrderServiceCancel;
    
    /**
     * 销售订单取消查询
     * @author LGQ
     * @date 2017年1月13日
     * @param queryParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qrySalesOrders(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderServiceCancel.querySalesOrdersCancel(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 申请取消
     * @author LGQ
     * @date 2017年1月13日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/apply/cancelApply", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void cancelApply(@RequestBody SalesOrderCancelDTO salesOrderCancelDTO,
                                           UriComponentsBuilder uriCB) {
        salesOrderServiceCancel.cancelApply(salesOrderCancelDTO);
    }
    
    /**
     * 取消订单
     * @author LGQ
     * @date 2017年1月13日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/cancel/cancelOrder", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void cancelOrder(@RequestBody SalesOrderCancelDTO salesOrderCancelDTO,
                                           UriComponentsBuilder uriCB) {
        salesOrderServiceCancel.cancelOrder(salesOrderCancelDTO);
    }
    
    
    

}
