/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : LabourPriceService1.java
*
 * @Author : zhengcong
 *
 * @Date : 2017年3月24日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月24日    zhengcong    1.0
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

import com.yonyou.dms.common.domains.DTO.basedata.ChargeTypeDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.service.basedata.ChargeTypeService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 
*收费类别controller
 * @author zhengcong
 * @date 2017年3月24日
 */

@Controller
@TxnConn
@RequestMapping("/basedata/chargetype")
public class ChargeTypeController extends BaseController{
	
    @Autowired
    private ChargeTypeService  ctservice;

    /**
     * 根据条件查询
 * @author zhengcong
 * @date 2017年3月24日
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryLabourPrice(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto  = ctservice.queryFee(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 根据code查询业务往来客户信息
     * @author zhengcong
     * @date 2017年3月24日
     */
    @RequestMapping(value = "/{MANAGE_SORT_CODE}/{IS_MANAGING}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> findByCode(@PathVariable(value = "MANAGE_SORT_CODE") String MANAGE_SORT_CODE,
    		@PathVariable(value = "IS_MANAGING") Object IS_MANAGING){
        return ctservice.findByCode(MANAGE_SORT_CODE,IS_MANAGING);
    }

    
    /**
     * 
     * 根据code修改信息
     * @author zhengcong
     * @date 2017年3月25日
     */

    @RequestMapping(value = "/{MANAGE_SORT_CODE}/{IS_MANAGING}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ChargeTypeDTO> modifyByCode(@PathVariable("MANAGE_SORT_CODE") String MANAGE_SORT_CODE,
    		@PathVariable("IS_MANAGING") String IS_MANAGING,
    		@RequestBody ChargeTypeDTO ctdto,
    		UriComponentsBuilder uriCB) throws ServiceBizException{
    	ctservice.modifyByCode(MANAGE_SORT_CODE,IS_MANAGING, ctdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/chargetype/{[MANAGE_SORT_CODE]}/{[IS_MANAGING]}").buildAndExpand(MANAGE_SORT_CODE,IS_MANAGING).toUriString());  
        return new ResponseEntity<ChargeTypeDTO>(headers, HttpStatus.CREATED);  
    }
    
   	/**
	 * 新增业务往来客户信息
	 * 
	 * @author zhengcong
	 * @date 2017年3月25日
	 */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ChargeTypeDTO> addChargeType(@RequestBody ChargeTypeDTO ctdto,UriComponentsBuilder uriCB) throws ServiceBizException{
    	ctservice.addFee(ctdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/chargetype/{[MANAGE_SORT_CODE]}/{[IS_MANAGING]}").buildAndExpand(ctdto.getManageSortCode(),ctdto.getAddItemRate()).toUriString());  
        return new ResponseEntity<ChargeTypeDTO>(ctdto,headers, HttpStatus.CREATED);  
    } 

}
