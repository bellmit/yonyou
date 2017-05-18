
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerResoContoller.java
*
* @Author : zhengcong
*
* @Date : 2017年3月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月22日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.controller.basedata;

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

import com.yonyou.dms.customer.domains.DTO.basedata.CustomerResoDTO;
import com.yonyou.dms.customer.service.basedata.CustomerResoService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 业务往来客户
 * @author zhanshiwei
 * @date 2016年7月11日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/customerReso")
public class CustomerResoContoller extends BaseController {


	@Autowired
	private CustomerResoService customerResoService;

	/**
	 * 根据查询条件查询业务往来客户信息
	 * 
	 * @author zhanshiwei
	 * @date 2016年7月12日
	 * @param queryParam 查询条件
	 *            
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryCustomerInfo(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = customerResoService.queryContCustomer(queryParam);
		return pageInfoDto;
	}
	

	/**
	 * 新增业务往来客户信息
	 * 
	 * @author zhengcong
	 * @date 2017年3月22日
	 */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CustomerResoDTO> addPayType(@RequestBody CustomerResoDTO cudto,UriComponentsBuilder uriCB) throws ServiceBizException{
    	customerResoService.addCustomerReso(cudto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/customerReso/findByCustomertypecode/{CUSTOMER_TYPE_CODE}").buildAndExpand(cudto.getCustomer_type_code()).toUriString());  
        return new ResponseEntity<CustomerResoDTO>(cudto,headers, HttpStatus.CREATED);  
    }

	
    /**
     * 根据customer_type_code查询业务往来客户信息
     * @author zhengcong
     * @date 2017年3月22日
     */
    @RequestMapping(value = "/findByCustomertypecode/{CUSTOMER_TYPE_CODE}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> findByCustomerTypeCode(@PathVariable(value = "CUSTOMER_TYPE_CODE") String CUSTOMER_TYPE_CODE){
        return customerResoService.findByCustomerTypeCode(CUSTOMER_TYPE_CODE);
    }

    
    /**
     * 
     * 根据customer_type_code修改信息
     * @author zhengcong
     * @date 2017年3月22日
     */
    	
    @RequestMapping(value = "/modifyByCustomertypecode/{CUSTOMER_TYPE_CODE}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<CustomerResoDTO> updateStore(@PathVariable("CUSTOMER_TYPE_CODE") String CUSTOMER_TYPE_CODE,@RequestBody CustomerResoDTO cudto,
    		UriComponentsBuilder uriCB) throws ServiceBizException{
    	customerResoService.modifyByCustomerTypeCode(CUSTOMER_TYPE_CODE, cudto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/customerReso/modifyByCustomertypecode{[CUSTOMER_TYPE_CODE]}").buildAndExpand(CUSTOMER_TYPE_CODE).toUriString());  
        return new ResponseEntity<CustomerResoDTO>(headers, HttpStatus.CREATED);  
    }
   
    
    
    
    
}
