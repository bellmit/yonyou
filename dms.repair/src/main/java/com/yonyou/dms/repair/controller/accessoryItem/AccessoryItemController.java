/** 
 *Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Author : zhengcong
 *
 * @Date : 2017年4月20日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年4月20日    zhengcong    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.controller.accessoryItem;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.service.accessoryItem.AccessoryItemService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 工单附件项目Controller
 * @author zhengcong
 * @date 2017年4月20日
 */
@Controller
@TxnConn
@RequestMapping("/accessoryItem/AccessoryItem")
public class AccessoryItemController extends BaseController{

    @Autowired
    private AccessoryItemService aiService;


    /**
     * 
     * 附加项目查询下拉列表
     * @author zhengcong
     * @date 2017年4月20日
     */

    @RequestMapping(value="/ailist",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> aiList() throws ServiceBizException{
        List<Map> ailist = aiService.aiList();
        return ailist;  

    }
    
    
    /**
     * 
     * 收费区分查询下拉列表
     * @author zhengcong
     * @date 2017年4月20日
     */

    @RequestMapping(value="/cplist",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> cpList() throws ServiceBizException{
        List<Map> cplist = aiService.cpList();
        return cplist;  

    }

     
    

	
}
