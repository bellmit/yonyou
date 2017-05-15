
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : ReceiveMoneyController.java
*
* @Author : jcsi
*
* @Date : 2016年9月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月28日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.controller.balance;

import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.repair.domains.DTO.balance.ChargeDerateDTO;
import com.yonyou.dms.repair.domains.DTO.balance.ReceiveMoneyDTO;
import com.yonyou.dms.repair.service.balance.ReceiveMoneyService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 收款
* @author jcsi
* @date 2016年9月28日
*/

@Controller
@TxnConn
@RequestMapping("/balance/receiveMoney")
public class ReceiveMoneyController extends BaseController{
    
    @Autowired
    private ReceiveMoneyService receiveMoneyService;
    
    /**
    * 维修收款
    * @author jcsi
    * @date 2016年9月29日
    * @param receiveMoneyDTO
    * @param uriCB
    * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ReceiveMoneyDTO> saveReceiveMoney(@RequestBody @Valid ReceiveMoneyDTO receiveMoneyDTO, UriComponentsBuilder uriCB){
        Long id=receiveMoneyService.saveReceiveMoney(receiveMoneyDTO);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/balance/receiveMoney/{id}/receiveMoneys").buildAndExpand(id).toUriString());
        return new ResponseEntity<ReceiveMoneyDTO>(receiveMoneyDTO, headers, HttpStatus.CREATED);
    }
    
    /**
    * 根据结算单收费对象ID查询收款单
    * @author jcsi
    * @date 2016年9月28日
    * @param id
    * @return
     */
    @RequestMapping(value="/{id}/receiveMoneys",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> findReceiveMoneyByPayobjId(@PathVariable Long id){
        return receiveMoneyService.findReceiveMoneyByPayobjId(id);   
    }
    
    
    /**
    * 保存减免收款
    * @author jcsi
    * @date 2016年9月29日
    * @param chargeDerateDTO
    * @param uriCB
    * @return
     */
    @RequestMapping(value="/chargeDerate",method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ChargeDerateDTO> saveChargeDerate(@RequestBody @Valid ChargeDerateDTO chargeDerateDTO, UriComponentsBuilder uriCB){
        Long id=receiveMoneyService.saveChargeDerate(chargeDerateDTO);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/balance/receiveMoney/{id}/receiveMoneys").buildAndExpand(id).toUriString());
        return new ResponseEntity<ChargeDerateDTO>(chargeDerateDTO, headers, HttpStatus.CREATED);
    }
    
    /**
    * 收款减免明细
    * @author jcsi
    * @date 2016年10月8日
    * @param id
    * @return
     */
    @RequestMapping(value="/{id}/chargeDerates",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> findChargeDerate(@PathVariable Long id){
        return receiveMoneyService.findChargeDerate(id);
    }
    
    /**
    * 销账
    * @author jcsi
    * @date 2016年10月9日
    * @param id
     */
    @RequestMapping(value="/{id}/writeoffTag",method=RequestMethod.GET)
    @ResponseBody
    public void updateWriteoffTag(@PathVariable Long id){
        receiveMoneyService.updateWriteoffTag(id);
    }
    
    

}
