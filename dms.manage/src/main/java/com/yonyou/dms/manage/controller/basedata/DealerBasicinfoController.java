
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : DealerBasicinfoController.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年7月8日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月8日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.controller.basedata;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.TenantDealerMappingService;
import com.yonyou.dms.manage.domains.DTO.basedata.DealerBasicinfoDTO;
import com.yonyou.dms.manage.service.basedata.dealer.DealerBasicinfoService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 简要描述：经销商信息管理
 *
 * @author ZhengHe
 * @date 2016年7月6日
 */
@Controller
@RequestMapping("/basedata")
public class DealerBasicinfoController extends BaseController{
    
    @Autowired
    private DealerBasicinfoService dealerService;
    @Autowired
    private TenantDealerMappingService tenantDealerSerivce;

    /**
     * 获取经销商下拉框
     * @author ZhengHe
     * @date 2016年8月2日
     * @return
     */
    @RequestMapping(value="/dealers",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Map<String,String>> getAllDealer(){
        Map<String,Map<String,String>> tenantMapping = tenantDealerSerivce.getAll();
        return tenantMapping;
    }

    /**
     * 
     * 获取经销商基本信息
     * @author ZhengHe
     * @date 2016年7月7日
     * @param id
     * @return
     */
    @TxnConn
    @RequestMapping(value = "/dealer", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getDealerBasicinfoById() {
        Map<String,Object> dealerMap = dealerService.getDealerBasicinfo();
        Map<String,Object> resMap=new HashMap<>();//将key值大写变成小写
        for (Map.Entry entry :dealerMap.entrySet()) {
            Object key = entry.getKey();
            resMap.put(key.toString().toLowerCase(), dealerMap.get(key));
        }
        return resMap;
    }
    
    
    /**
     * 
     * 获取经销商基本信息
     * @author ZhengHe
     * @date 2016年7月7日
     * @param id
     * @return
     */
    @TxnConn
    @RequestMapping(value = "/searchemployees", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto getDealerBasicinfoPerson(@RequestParam Map<String,String> queryParam) {       
        return dealerService.getDealerBasicinfoMain(queryParam);
    }

    
    /**
     * 修改经销商基本信息
     * @author ZhengHe
     * @date 2016年7月7日
     * @param id
     * @param dbdto经销商基本信息
     * @param uriCB
     * @return
     */
    @TxnConn
    @RequestMapping(value="/dealer",method=RequestMethod.PUT)
    public ResponseEntity<DealerBasicinfoDTO> modifyLabourPrice(@RequestBody DealerBasicinfoDTO dbdto,UriComponentsBuilder uriCB){
        dealerService.modifyBasicinfo(dbdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/dealer").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);  
    }
}

