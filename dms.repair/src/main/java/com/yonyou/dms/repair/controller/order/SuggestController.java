
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : SuggestController.java
*
* @Author : jcsi
*
* @Date : 2016年9月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月9日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.controller.order;

import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.repair.domains.DTO.order.SuggestDTO;
import com.yonyou.dms.repair.service.order.SuggestService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 维修建议
* @author jcsi
* @date 2016年9月9日
*/
@Controller
@TxnConn
@RequestMapping("/order/suggests")
public class SuggestController extends BaseController{
    
    @Autowired
    private SuggestService suggestService;
    
    /**
    * 保存建议维修项目、建议配件
    * @author jcsi
    * @date 2016年9月9日
    * @param labourDto
    * @param partDto
    * @param uriCB
    * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<SuggestDTO> saveSuggest(@RequestBody SuggestDTO suggest,UriComponentsBuilder uriCB){
        Long id= suggestService.saveSuggest(suggest);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location",uriCB.path("/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<SuggestDTO>(suggest,  headers, HttpStatus.CREATED);
    }
    
    /**
    * 建议维修项目查询
    * @author jcsi
    * @date 2016年10月12日
    * @param id
    * @return
     */
    @RequestMapping(value="/{vin}/suggestLabours",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> findSuggestLabour(@PathVariable String vin){
       return suggestService.findSuggestLabour(vin);
    }
    
    /**
     * 建议维修配件查询
     * @author jcsi
     * @date 2016年10月12日
     * @param id
     * @return
      */
     @RequestMapping(value="/{vin}/sugParts",method=RequestMethod.GET)
     @ResponseBody
     public List<Map> findSuggestPart(@PathVariable String vin){
        return suggestService.findSuggestPart(vin);
     }
     
     /**
     * 修改
     * @author jcsi
     * @date 2016年10月12日
     * @param suggest
      */
     @RequestMapping(method=RequestMethod.PUT)
     @ResponseBody
     public void editSuggest(@RequestBody SuggestDTO suggest){
         suggestService.editSuggest(suggest);
     }

    
}
