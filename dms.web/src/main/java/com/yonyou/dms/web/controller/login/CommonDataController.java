
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.web
*
* @File name : CommonDataController.java
*
* @Author : zhangxc
*
* @Date : 2016年7月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月13日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.web.controller.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domain.UserAccessInfoDto;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.f4.mvc.annotation.NoTxn;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* 获取常规数据
* @author zhangxc
* @date 2016年7月13日
*/
@Controller
@TxnConn
@RequestMapping("/common/commonDatas")
public class CommonDataController {

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(CommonDataController.class);
    @Autowired
    private SystemParamService  systemparamservice;
    /**
     * 
    * 获得常规数据(包括登陆信息等）
    * @author zhangxc
    * @date 2016年7月13日
    * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getCommonData() {
        Map<String,Object> commonDataMap = new HashMap<>();
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        commonDataMap.put("userInfo", loginInfo);
        //放系统参数信息
        Map<String,Map<String,Object>> systemParamMap = systemparamservice.queryBasicParameters();
        commonDataMap.put("systemParam", systemParamMap);
        
        return commonDataMap;
    }
    
    /**
     * 
    * 获得常规数据(包括登陆信息等）
    * @author zhangxc
    * @date 2016年7月13日
    * @return
     */
    @RequestMapping(value = "/getCurrentToken",method = RequestMethod.GET)
    @ResponseBody
    @NoTxn
    public String getCurrentToken(HttpServletRequest request) {
      UserAccessInfoDto userAccessInfoDto = ApplicationContextHelper.getBeanByType(UserAccessInfoDto.class);
        return userAccessInfoDto.getValidFirstToken();
    }
}
