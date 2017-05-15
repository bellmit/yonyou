
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartLossController.java
*
* @Author : jcsi
*
* @Date : 2016年8月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月13日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.controller.stockmanage;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartLossDto;
import com.yonyou.dms.part.domains.PO.stockmanage.PartLossPo;
import com.yonyou.dms.part.service.stockmanage.PartLossService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 配件报损
* @author jcsi
* @date 2016年8月13日
*/

@Controller
@TxnConn
@RequestMapping("/stockmanage/partloss")
public class PartLossController extends BaseController{

    @Autowired
    private PartLossService partLossService;
    
    @Autowired
    private CommonNoService commonNoService;
    
    /**
    * 查询
    * @author jcsi
    * @date 2016年8月13日
    * @param param
    * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto searchPartLoss(@RequestParam Map<String, String> param){
       return partLossService.searchPartLoss(param);  
    }
    
    /**
    * 根据id删除
    * @author jcsi
    * @date 2016年8月13日
    * @param id
     */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteById(@PathVariable Long id){
        partLossService.deleteById(id);
    }
    
    /**
    * 新增
    * @author jcsi
    * @date 2016年8月13日
    * @param partLossDto
    * @param uriCB
    * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> addPartLoss(@RequestBody @Valid PartLossDto partLossDto,UriComponentsBuilder uriCB){
        PartLossPo lossPo=partLossService.addPartLoss(partLossDto,commonNoService.getSystemOrderNo(CommonConstants.PART_LOSS_NO));
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/stockmanage/partloss/{id}").buildAndExpand(lossPo.getLongId()).toUriString());
        return new ResponseEntity<Map<String,Object>>(lossPo.toMap(), headers, HttpStatus.CREATED);
    }
    
    /**
    * 修改
    * @author jcsi
    * @date 2016年8月13日
    * @return
     */
    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<PartLossDto> editPartLoss(@PathVariable Long id,@RequestBody @Valid PartLossDto partLossDto,UriComponentsBuilder uriCB){
        partLossService.editPartLoss(id, partLossDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/stockmanage/partloss/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<PartLossDto>(partLossDto,headers, HttpStatus.CREATED);
    }
    
    /**
    * 根据id查询主单信息
    * @author jcsi
    * @date 2016年8月13日
    * @param id
    * @return
     */
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map findById(@PathVariable Long id){
        return partLossService.findLossById(id);
    }
    
    /**
    * 根据主单id查询子表信息
    * @author jcsi
    * @date 2016年8月13日
    * @param id
    * @return
     */
    @RequestMapping(value="/{id}/lossitems",method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findItemById(@PathVariable Long id){
        return partLossService.findLossItemById(id);
    }
    
    /**
    * 入账
    * @author jcsi
    * @date 2016年8月14日
    * @param id
     */
    @RequestMapping(value="/{id}/orderstatus",method=RequestMethod.GET)
    @ResponseBody
    public void updateOrderStatus(@PathVariable Long id){
        partLossService.updateOrderStatus(id);
    }
    
    
    
}
