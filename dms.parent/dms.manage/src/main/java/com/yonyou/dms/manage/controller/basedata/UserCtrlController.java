
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : UserCtrlController.java
*
* @Author : zhanshiwei
*
* @Date : 2016年12月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月26日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.controller.basedata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.manage.domains.DTO.basedata.CommonTreeDto;
import com.yonyou.dms.manage.service.basedata.user.UserCtrlService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 账号受控权限
 * 
 * @author zhanshiwei
 * @date 2016年12月26日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/userCtrl")
public class UserCtrlController {

    @Autowired
    private UserCtrlService userctrlservice;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> UserCtrlOption(@PathVariable(value = "id") String id) {
        List<CommonTreeDto> userCtrlResult = userctrlservice.UserCtrlOption(id);
        Map<String, Object> userCtrlData = new HashMap<String, Object>();
        userCtrlData.put("userCtrl", userCtrlResult);
        return userCtrlData;
    }

    @RequestMapping(value = "/{ctrlCode}/getBusinessPurview", method = RequestMethod.GET)
    @ResponseBody
    public Map GetBusinessPurview(@PathVariable(value = "ctrlCode") String ctrlCode) {
        return userctrlservice.GetBusinessPurview(ctrlCode);
    }
}
