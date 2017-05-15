
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : StatisticalDataController.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月23日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月23日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.report.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.report.service.impl.StatisticalDataService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 首页统计
 * 
 * @author zhanshiwei
 * @date 2016年9月23日
 */
@Controller
@TxnConn
@RequestMapping("/homePage/statistical")
public class StatisticalDataController extends BaseController {

    @Autowired
    private StatisticalDataService statisticaldataservice;

    /**
     * 统计投诉未处理个数
     * 
     * @author zhanshiwei
     * @date 2016年9月21日
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryComplainCounts() {
        Map<String, Object> opetypeMap = statisticaldataservice.StatisticalData();
        return opetypeMap ;
    }
    
    
    /**
    * 售后维修统计
    * @author zhanshiwei
    * @date 2016年11月24日
    * @return
    */
    	
    @RequestMapping(value="/repairs",method=RequestMethod.GET)
    @ResponseBody
    public List<List<Object>> queryRepairs(){
        List<List<Object>> result=statisticaldataservice.queryRepairs();
        return result;
    }
    
    /**
    * 售后维修统计合计
    * @author zhanshiwei
    * @date 2016年11月25日
    * @return
    */
    	
    @RequestMapping(value="/repairs/summation",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> queryRepairCon(){
        List<Map> result=statisticaldataservice.queryRepairCon();
        return result;
    }
    
}
