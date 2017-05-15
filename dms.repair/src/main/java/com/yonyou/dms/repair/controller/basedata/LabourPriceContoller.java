
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : LabourPriceContoller.java
 *
 * @Author : zhengcong
 *
 * @Date : 2017年3月23日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月23日    zhengcong    1.0
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.LabourPriceDTO;
import com.yonyou.dms.repair.service.basedata.LabourPriceService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;


/**
 * 简要描述：工时管理控制类 通过此类对工时单进行增删改查操作
 * 
 * @author zhengcong
 * @date 2017年3月23日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/labours")
public class LabourPriceContoller extends BaseController{

    @Autowired
    private LabourPriceService  lpservice;

    /**
     * 根据条件查询工时单价的信息
 * @author zhengcong
 * @date 2017年3月23日
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryLabourPrice(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto  = lpservice.QueryLabourPrice(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 根据code查询业务往来客户信息
     * @author zhengcong
     * @date 2017年3月22日
     */
    @RequestMapping(value = "/{LABOUR_PRICE_CODE}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> findByCustomerTypeCode(@PathVariable(value = "LABOUR_PRICE_CODE") String LABOUR_PRICE_CODE){
        return lpservice.findByCode(LABOUR_PRICE_CODE);
    }

    
    /**
     * 
     * 根据code修改信息
     * @author zhengcong
     * @date 2017年3月22日
     */
    	
    @RequestMapping(value = "/{LABOUR_PRICE_CODE}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<LabourPriceDTO> updateStore(@PathVariable("LABOUR_PRICE_CODE") String LABOUR_PRICE_CODE,@RequestBody LabourPriceDTO cudto,
    		UriComponentsBuilder uriCB) throws ServiceBizException{
    	lpservice.modifyByCode(LABOUR_PRICE_CODE, cudto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/labours/{[LABOUR_PRICE_CODE]}").buildAndExpand(LABOUR_PRICE_CODE).toUriString());  
        return new ResponseEntity<LabourPriceDTO>(headers, HttpStatus.CREATED);  
    }
    
   	/**
	 * 新增业务往来客户信息
	 * 
	 * @author zhengcong
	 * @date 2017年3月22日
	 */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<LabourPriceDTO> addPayType(@RequestBody LabourPriceDTO cudto,UriComponentsBuilder uriCB) throws ServiceBizException{
    	lpservice.addLabourPrice(cudto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/customerReso/findByCustomertypecode/{LABOUR_PRICE_CODE}").buildAndExpand(cudto.getLabourPrice()).toUriString());  
        return new ResponseEntity<LabourPriceDTO>(cudto,headers, HttpStatus.CREATED);  
    } 


    /**
     * 
     * 删除
	 * @author zhengcong
	 * @date 2017年3月22日
     */
    @RequestMapping(value="/{LABOUR_PRICE_CODE}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabourPrice(@PathVariable("LABOUR_PRICE_CODE") String LABOUR_PRICE_CODE){
        lpservice.deleteLabourPrice(LABOUR_PRICE_CODE);
    }
    /**
     * 工时单价下拉框加载
     * @author wantao
     * @date 2017年4月20日
     * @return
     */
    @RequestMapping(value="/laboursPriceDict/dicts",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> selectLabourPrice() {
        List<Map> lplist = lpservice.selectLabourPrice();
        return lplist;
    }

}
