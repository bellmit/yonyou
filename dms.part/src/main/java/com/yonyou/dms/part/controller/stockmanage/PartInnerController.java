
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartInnerController.java
*
* @Author : jcsi
*
* @Date : 2016年8月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月15日    jcsi    1.0
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
import com.yonyou.dms.part.domains.DTO.stockmanage.PartInnerDto;
import com.yonyou.dms.part.domains.PO.stockmanage.PartInnerPo;
import com.yonyou.dms.part.service.stockmanage.PartInnerService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 内部领用
* @author jcsi
* @date 2016年8月15日
*/

@Controller
@TxnConn
@RequestMapping("/stockmanage/partinners")
public class PartInnerController extends BaseController{
    
    
    @Autowired
    private PartInnerService partInnerService;
    
    @Autowired
    private CommonNoService commonNoService;
    

    /**
    * 查询
    * @author jcsi
    * @date 2016年8月15日
    * @param param
    * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto search(@RequestParam Map<String, String> param){
        return partInnerService.search(param);
    }
    
    /**
    * 根据id删除信息
    * @author jcsi
    * @date 2016年8月15日
    * @param id
     */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteById(@PathVariable Long id){
        partInnerService.deleteById(id);
    }
    
    /**
    * 新增
    * @author jcsi
    * @date 2016年8月15日
    * @param partInnerDto
    * @param uriCB
    * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> addPartInner(@RequestBody @Valid PartInnerDto partInnerDto,UriComponentsBuilder uriCB){
        PartInnerPo partInnerPo=partInnerService.addPartInner(partInnerDto, commonNoService.getSystemOrderNo(CommonConstants.PART_INNER_NO));
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/stockmanage/partinners/{id}").buildAndExpand(partInnerPo.getLongId()).toUriString());
        return new ResponseEntity<Map<String,Object>>(partInnerPo.toMap(), headers, HttpStatus.CREATED);
    
    }
    
    /**
    * 修改
    * @author jcsi
    * @date 2016年8月15日
    * @param id
    * @param partInnerDto
    * @param uriCB
    * @return
     */
    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<PartInnerDto> editPartInner(@PathVariable Long id,@RequestBody @Valid PartInnerDto partInnerDto,UriComponentsBuilder uriCB){
        partInnerService.editPartInner(id, partInnerDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/stockmanage/partinners/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<PartInnerDto>(partInnerDto,headers, HttpStatus.CREATED);
    }
    
    /**
    * 根据id查询主单信息
    * @author jcsi
    * @date 2016年8月15日
    * @param id
    * @return
     */
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findById(@PathVariable Long id){
        return partInnerService.findById(id);
    }
    
    /**
    * 根据主表id查询字表信息
    * @author jcsi
    * @date 2016年8月15日
    * @param id
    * @return
     */
    @RequestMapping(value="/{id}/partinneritems")
    @ResponseBody
    public PageInfoDto findItemById(@PathVariable Long id){
        return partInnerService.findItemById(id);
    }
    
    /**
    * 入账
    * @author jcsi
    * @date 2016年10月30日
    * @param id
     */
    @RequestMapping(value="/{id}/orderstatus")
    @ResponseBody
    public void updateOrderStatus(@PathVariable Long id){
         partInnerService.updateOrderStatus(id);
    }
    
}
