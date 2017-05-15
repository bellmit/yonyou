
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartCustomerDealerController.java
*
* @Author : chenwei
*
* @Date : 2017年3月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月29日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.controller.basedata;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.part.service.basedata.PartCustomerDealerService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 业务往来客户经销商
* @author chenwei
* @date 2017年3月29日
*/
@Controller
@TxnConn
@RequestMapping("/basedata/partCustomerDealer")
public class PartCustomerDealerController extends BaseController{

    @Autowired
    private PartCustomerDealerService partCustomerDealerService;
    
    /**
     * 
     * 提供业务往来客户经销商的方法
     * @author chenwei
     * @date 2017年3月28日
     * @param queryParam
     * @return
     */
    @RequestMapping(value="/searchDealers",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> dealersSelect(@RequestParam Map<String,String> queryParam) {
        List<Map> customerslist = partCustomerDealerService.queryPartCustomerDealerList(queryParam);
        return customerslist;
    } 
    
}
