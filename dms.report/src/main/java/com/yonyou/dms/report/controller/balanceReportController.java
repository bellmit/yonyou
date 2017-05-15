
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : balanceReport.java
*
* @Author : ZhengHe
*
* @Date : 2016年10月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月26日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.report.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.report.service.impl.balanceRepartService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* 结算报表
* @author ZhengHe
* @date 2016年10月26日
*/
@Controller
@TxnConn
@RequestMapping("/balanceReport/balanceAccounts")
public class balanceReportController {
    
    @Autowired
    private balanceRepartService brServcie;
    
    
    /**
    * 查询结算单
    * @author ZhengHe
    * @date 2016年10月26日
    * @param param
    * @return
    */
    	
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryBalance(@RequestParam Map<String, String> param){
        return brServcie.queryBalance(param);
    }

}
