
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : DiscountModeController.java
 *
 * @Author : zhengcong
 *
 * @Date : 2017年3月23日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月23日   zhengcong   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.controller.basedata;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.DiscountModeDTO;
import com.yonyou.dms.repair.service.basedata.DiscountModeService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 优惠模式
 * @author zhengcong
 * @date 2017年3月23日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/discountmodes")
public class DiscountModeController extends BaseController {

    @Autowired
    private DiscountModeService   modeservice; 

    /**
     * 查询
    * @author zhengcong
    * @date 2017年3月23日
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDiscountMode(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = modeservice.QueryDiscountMode(queryParam);
        return pageInfoDto;
    }

    /**
     * 根据code查询业务往来客户信息
     * @author zhengcong
     * @date 2017年3月23日
     */
    @RequestMapping(value = "/findByCode/{DISCOUNT_MODE_CODE}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> findByCode(@PathVariable(value = "DISCOUNT_MODE_CODE") String DISCOUNT_MODE_CODE){
        return modeservice.findByCode(DISCOUNT_MODE_CODE);
    }
    
    
    /**
     * 
     * 根据code修改信息
     * @author zhengcong
     * @date 2017年3月23日
     */
    	
    @RequestMapping(value = "/modifyByCode/{DISCOUNT_MODE_CODE}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<DiscountModeDTO> updateStore(@PathVariable("DISCOUNT_MODE_CODE") String DISCOUNT_MODE_CODE,@RequestBody DiscountModeDTO cudto,
    		UriComponentsBuilder uriCB) throws ServiceBizException{
    	modeservice.modifyByCode(DISCOUNT_MODE_CODE, cudto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/discountmodes/findByCode/{[DISCOUNT_MODE_CODE]}").buildAndExpand(DISCOUNT_MODE_CODE).toUriString());  
        return new ResponseEntity<DiscountModeDTO>(headers, HttpStatus.CREATED);  
    }
    
    
	/**
	 * 新增
	 * 
	 * @author zhengcong
	 * @date 2017年3月23日
	 */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DiscountModeDTO> addPayType(@RequestBody DiscountModeDTO cudto,UriComponentsBuilder uriCB) throws ServiceBizException{
    	modeservice.addDiscountMode(cudto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/discountmodes/findByCode/{[DISCOUNT_MODE_CODE]}").buildAndExpand(cudto.getDiscountModeCode()).toUriString());  
        return new ResponseEntity<DiscountModeDTO>(cudto,headers, HttpStatus.CREATED);  
    }
    
    /**
     * 优惠模式下拉框
     * @return
     */
    @RequestMapping(value = "/queryDiscountMode", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryDiscountMode() {
		return modeservice.queryDiscountMode();
	}


}
