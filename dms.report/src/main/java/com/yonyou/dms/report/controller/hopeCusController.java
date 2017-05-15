
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : hopeCusController.java
*
* @Author : zhanshiwei
*
* @Date : 2017年1月20日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月20日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.report.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.report.service.impl.queryHopeCusServiceImpl;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 有望客户跟踪进度
 * 
 * @author zhanshiwei
 * @date 2017年1月20日
 */
@Controller
@TxnConn
@RequestMapping("/hopeCustomer")
public class hopeCusController {

    @Autowired
    private queryHopeCusServiceImpl hopeCusServiceImpl;

	@RequestMapping(value = "/queryHopeCustomer", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryHopeCustomer(@RequestParam Map<String, String> param) throws ServiceBizException, ParseException {
    	PageInfoDto num=hopeCusServiceImpl.queryHopeCustomer(param);
        return num;
    }
    
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryHopeCustomer/{startDate}/dict/{soldBy}/duct", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> printHopeCustomer(@PathVariable String startDate,@PathVariable String soldBy) {
    	
    	String soldBys="";
    	System.err.println("*****"+soldBy);
    	if(soldBy.equals("1")){
    		soldBys="";
    	}else{
    		soldBys=soldBy;
    	}
        return hopeCusServiceImpl.printHopeCustomer(startDate,soldBys);
    }
}
