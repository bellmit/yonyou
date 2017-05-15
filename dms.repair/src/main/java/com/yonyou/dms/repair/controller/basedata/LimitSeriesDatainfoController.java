
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.part
 *
 * @File name : StoreController.java
 *
 * @Author : zhongshiwei
 *
 * @Date : 2016年7月10日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月10日    zhongshiwei    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.controller.basedata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.LimitSeriesDatainfoPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.MaintainWorkTypeDTO;
import com.yonyou.dms.repair.service.basedata.LimitSeriesDatainfoService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 限价车系及维修类型信息
 * @author chenwei
 * @date 2017年3月27日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/limitSeriesDatainfo")
public class LimitSeriesDatainfoController extends BaseController{

    @Autowired
    private LimitSeriesDatainfoService limitSeriesDatainfoService;
    
    /**
     * 根据labourPositionCode查询
    * TODO 
     * @author chenwei
     * @date 2017年3月23日
    * @param labourPositionCode
    * @return
     */
    @RequestMapping(value = "/{itemId}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findByPrimaryKey(@PathVariable(value = "itemId") String itemId){
    	LimitSeriesDatainfoPO limitSeriesDatainfoPO = limitSeriesDatainfoService.findByPrimaryKey(itemId);
    	return limitSeriesDatainfoPO.toMap();
    }
    
    
    /**
     * 根据查询条件返回对应的用户数据
     * @author chenwei
     * @date 2017年3月27日
     * @param searchlimitSeriesDatainfo 查询条件
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto limitSeriesDatainfoSql(@RequestParam Map<String, String> limitSeriesDatainfoSQL) throws ServiceBizException{
        PageInfoDto pageInfoDto = limitSeriesDatainfoService.searchLimitSeriesDatainfo(limitSeriesDatainfoSQL);
        return pageInfoDto;
    }
    
}
