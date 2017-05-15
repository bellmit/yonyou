
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : PlatformController.java
*
* @Author : Administrator
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
	
package com.yonyou.dms.manage.controller.basedata;

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
import com.yonyou.dms.manage.domains.DTO.platform.PlatformDto;
import com.yonyou.dms.manage.service.basedata.platform.PlatformService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* TODO 销售顾问/经理工作平台
* @author Administrator
* @date 2016年12月20日
*/
@Controller
@TxnConn
@RequestMapping("/basedata/platform")
public class PlatformController {
    
    @Autowired
    private PlatformService    platformService;
    
    
    
    
    /** 
     * 获取工作平台信息
     * @author ZhengHe
     * @date 2016年7月15日
     * @param queryParams
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public Map getPlatformCount(@RequestParam Map<String, String> queryParams){
        Map pageInfoDto =platformService.queryPlatformCount();
        return pageInfoDto ;
    }
    
    /** 
     * 获取潜客信息
     * @author ZhengHe
     * @date 2016年7月15日
     * @param queryParams
     * @return
     */
    @RequestMapping(value = "/customer",method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto getPtenticalCustomer(@RequestParam Map<String, String> queryParams){
        PageInfoDto pageInfoDto =platformService.queryPotentialCustomer(queryParams);
        
        return pageInfoDto ;
    }
    
    /**
     * 
     * 根据id获取挂靠单位信息
     * @author ZhengHe
     * @date 2016年7月18日
     * @param id
     * @return
     */
    @RequestMapping(value = "/salesOrder", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAttachUnitById(@PathVariable(value ="id") Long id){
        return null ;
    }
    
    /**
     * 
     * 根据id修改预计成交时段
     * @author 
     * @date 2016年7月18日
     * @param id
     * @return
     */
    @RequestMapping(value = "/exceptTimesRange/noList", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void modifyExceptTimesRange(@RequestBody PlatformDto platformDto,
                                           UriComponentsBuilder uriCB) {
        platformService.modifyExceptTimesRange(platformDto);
    }
    
    
    
    
    
    
    
}
