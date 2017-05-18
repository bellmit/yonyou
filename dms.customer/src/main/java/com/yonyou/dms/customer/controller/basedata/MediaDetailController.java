
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : MediaDetailController.java
*
* @Author : wangxin
*
* @Date : 2016年12月20日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月20日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.customer.controller.basedata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.customer.service.basedata.MediaDetailService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* TODO description
* @author wangxin
* @date 2016年12月20日
*/

@Controller
@TxnConn
@RequestMapping("/basedata/mediaDetail")
public class MediaDetailController extends BaseController {
	
    @Autowired
    private MediaDetailService mediaDetailService;
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findAll(@RequestParam Map<String, String> queryParam){
        PageInfoDto pageInfoDto = mediaDetailService.findAllMediaDetail(queryParam);
        return pageInfoDto;
    }
}
