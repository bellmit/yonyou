package com.yonyou.dms.customer.controller.basedata;

import java.util.HashMap;
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

import com.yonyou.dms.common.domains.DTO.basedata.BigCustomerDTO;
import com.yonyou.dms.common.domains.DTO.basedata.BigCustomerRepayDTO;
import com.yonyou.dms.customer.service.customerManage.BigCustomerManageRepayService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.f4.mvc.annotation.TxnConn;

/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : BigCustomerManageRepayController.java
*
* @Author : Administrator
*
* @Date : 2017年1月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月18日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

/**
* TODO description
* @author Administrator
* @date 2017年1月18日
*/
@Controller
@TxnConn
@RequestMapping("/BigCustomer/repay")
public class BigCustomerManageRepayController {
    @Autowired
    private BigCustomerManageRepayService bigcustomermanagerepay;
    
    //审批单查询
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryBigCustomerWs(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = bigcustomermanagerepay.queryBigCustomerWs(queryParam);
        return pageInfoDto;
    }
    //审批车辆
    @RequestMapping(value = "/sales/{id}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryBigCustomerWsCar(@PathVariable(value = "id") String id) {
        PageInfoDto pageInfoDto = bigcustomermanagerepay.queryBigCustomerWsCar(id);
        return pageInfoDto;
    }
    //报备单查询
    @RequestMapping(value = "/bigcustomer", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryBigCustomer(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = bigcustomermanagerepay.queryBigCustomer(queryParam);
        return pageInfoDto;
    }
  //销售单查询 
    @RequestMapping(value = "/{wsAppType}/and/{firstsubmittime}/sales", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querySales(@RequestParam Map<String, String> queryParam,@PathVariable("wsAppType") String wsAppType,@PathVariable("firstsubmittime") String firstsubmittime) {
        PageInfoDto pageInfoDto = bigcustomermanagerepay.querySales(queryParam,wsAppType,firstsubmittime);
        return pageInfoDto;
    }
    //校验品牌车型车系
    @RequestMapping(value = "/{brandCode}/{seriesCode}/{wsType}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> BigCusSaveBeforeEvent(@RequestParam Map<String, String> queryParam,@PathVariable("brandCode") String brandCode,@PathVariable("seriesCode") String seriesCode,@PathVariable("wsType") String wsType) {
        Map<String, Object> result = bigcustomermanagerepay.employeSaveBeforeEvent(queryParam,brandCode,seriesCode,wsType);
        System.out.println(result);
     
       
        return result;
    }
    @RequestMapping(value = "/checkdata", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> BigCusSaveBeforeEvent(@RequestParam Map<String, String> queryParam) {
        Map<String, Object> result = bigcustomermanagerepay.employeSaveBeforeEventAmount(queryParam);
        System.out.println(result);
        if(result!=null&&!result.isEmpty()){
            result.put("success", "false");     
        }else{
            result=new HashMap<String, Object>();
            result.put("success", "true"); 
        }
        return result;
    }
    //修改查车
    @RequestMapping(value = "{WS_NO}/car",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryBigCustomerWsCarbyWsNo(@RequestParam Map<String, String> queryParam,@PathVariable("WS_NO") String wsNo) {
        PageInfoDto pageInfoDto = bigcustomermanagerepay.queryBigCustomerWsCarbyWsNo(queryParam,wsNo);
        return pageInfoDto;
    }
   //修改查报备单
    @RequestMapping(value = "{WS_NO}/in",method = RequestMethod.GET)
    @ResponseBody
    public Map queryOwnerCusByEmployeeNo(@PathVariable(value = "WS_NO") String wsNo) {       
        Map pp=new HashMap();
        List<Map> addressList = bigcustomermanagerepay.queryOwnerCusBywsNo(wsNo);
        if (addressList.size()>0){
            pp.putAll(addressList.get(0));  
        }
        return pp;
    }
    //新增保存
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public  ResponseEntity<Map<String, Object>> addOwnerCusInfo(@RequestBody  BigCustomerRepayDTO bigCustomerDto,
                                                                UriComponentsBuilder uriCB) {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        String WsNo = bigcustomermanagerepay.addBiGCusRepayInfo(bigCustomerDto
                                                          );
     
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/BigCustomer/repay").buildAndExpand(WsNo).toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/{WS_NO}/save",method = RequestMethod.PUT)
    @ResponseBody
    public  ResponseEntity<Map<String, Object>> modifyOwnerCusInfo(@RequestBody  BigCustomerRepayDTO bigCustomerDto,
                                                                UriComponentsBuilder uriCB,@PathVariable("WS_NO") String wsNo) {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
   //     String WsNo = bigcustomermanage.addBiGCusInfo(bigCustomerDto,
   //                                                       commonNoService.getSystemOrderNo(CommonConstants.SRV_PSSPDH));
        String WsNo = bigcustomermanagerepay.modifyBiGCusInfo(bigCustomerDto,wsNo);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/BigCustomer/manage").buildAndExpand(WsNo).toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }
    /**
     * 检查潜客电话
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/Check1/and/Status/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String CheckStatus(@PathVariable(value = "id") String id) {
        String pageInfoDto = bigcustomermanagerepay.CheckStatus(id); 
        return pageInfoDto;
    }
    
    /**
     * 检查潜客电话
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/Check1/and/Status1/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String CheckStatus1(@PathVariable(value = "id") String id) {
        String pageInfoDto = bigcustomermanagerepay.CheckStatus1(id); 
        return pageInfoDto;
    }
    
    /**
     * 检查潜客电话
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/upLoadCheck/{wsNo}/CheckDateDetail", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> CheckDateDetail(@PathVariable(value = "wsNo") String wsNo) {
       List<Map> pageInfoDto = bigcustomermanagerepay.CheckDateDetail(wsNo); 
        return pageInfoDto;
    }
    //上报
    @RequestMapping(value = "/upLoad/DSO0402/{WS_NO}",method = RequestMethod.PUT)
    @ResponseBody
    public  ResponseEntity<Map<String, Object>> bigCustomerDMSToDCS(@RequestBody  BigCustomerRepayDTO bigCustomerDto,
                                                                UriComponentsBuilder uriCB,@PathVariable("WS_NO") String wsNo) {
        bigcustomermanagerepay.uploanBigCustomer(wsNo);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/BigCustomer/manage").buildAndExpand(wsNo).toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }
}
