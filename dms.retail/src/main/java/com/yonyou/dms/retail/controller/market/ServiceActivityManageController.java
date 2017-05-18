
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : ServiceActivityManageController.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月16日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.controller.market;

import java.util.Map;

import javax.validation.Valid;

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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.retail.domains.DTO.market.ServiceActivityManageDTO;
import com.yonyou.dms.retail.service.market.ServiceActivityManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月16日
*/
@Controller
@TxnConn
@RequestMapping("/market/serviceActivityManage")
@SuppressWarnings("rawtypes")
public class ServiceActivityManageController {
    
    @Autowired
    private ExcelGenerator excelService;
    
    @Autowired
    private ServiceActivityManageService activityManageService;
    
    /**
     * 查询
     * @author dingchaoyu
     * @date 2016年7月27日
     * @param queryParam
     * @return
     */

    @RequestMapping(value="/query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto query(@RequestParam Map map) {
        return activityManageService.query(map);
    }
    
    /**
     * 查询
     * @author dingchaoyu
     * @date 2016年7月27日
     * @param queryParam
     * @return
     */

    @RequestMapping(value="/query/{ACTIVITY_CODE}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> querys(@PathVariable("ACTIVITY_CODE") String id, UriComponentsBuilder uriCB) {
        return activityManageService.querys(id);
    }
    
    /**
     * 保存
     */
    @RequestMapping(value="/save",method = RequestMethod.PUT)
    public ResponseEntity<String> performExecutes(@RequestBody ServiceActivityManageDTO dto, UriComponentsBuilder uriCB){
       String s = activityManageService.save(dto);
       MultiValueMap<String, String> headers = new HttpHeaders();
       headers.set("Location", uriCB.path("/market/serviceActivityManage").buildAndExpand().toUriString());
       return new ResponseEntity<String>(s, headers, HttpStatus.CREATED);
    }
    
}
