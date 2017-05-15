
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : AttachUnitController.java
*
* @Author : Administrator
*
* @Date : 2016年12月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月16日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.controller.basedata;

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
import com.yonyou.dms.manage.domains.DTO.basedata.AttachUnitDto;
import com.yonyou.dms.manage.domains.PO.basedata.AttachUnitPo;
import com.yonyou.dms.manage.service.basedata.attachUnit.AttachUnitService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 车辆信息挂靠单位管理
* @author Administrator
* @date 2016年12月16日
*/

@Controller
@TxnConn
@RequestMapping("/basedata/attachunit")
public class AttachUnitController extends BaseController {
    @Autowired
    private AttachUnitService aus;
    
    /** 
     * 获取所有职务信息
     * @author ZhengHe
     * @date 2016年7月15日
     * @param queryParams
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto getAttachUnits(@RequestParam Map<String, String> queryParams){
        return aus.queryAttachUnit(queryParams);
    }
    
    /**
     * 新增挂靠单位信息
     * @author ZhengHe
     * @date 2016年7月18日
     * @param ptdto
     * @param uriCB
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AttachUnitDto> addAttachUnit(@RequestBody AttachUnitDto ptdto,UriComponentsBuilder uriCB){
        Long id =aus.addAttachUnit(ptdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/attachunit/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<AttachUnitDto>(ptdto,headers, HttpStatus.CREATED);  
    }
    
    /**
     * 
     * 根据id获取挂靠单位信息
     * @author ZhengHe
     * @date 2016年7月18日
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAttachUnitById(@PathVariable(value ="id") Long id){
        AttachUnitPo ptPo=aus.getAttachUnitById(id);
        return ptPo.toMap();
    }
    
    /**
     * 根据ID 删除挂靠单位信息
     * 
     * @author zhangxc
     * @date 2016年12月15日
     * @param id
     * @param uriCB
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value ="id") Long id, UriComponentsBuilder uriCB) {
        aus.deleteAttachUnitById(id);
    }
    
    /**
     * 
     * 根据id修改挂靠单位信息
     * @author ZhengHe
     * @date 2016年7月18日
     * @param id
     * @param ptdto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<AttachUnitDto> modifyAttachUnit(@PathVariable(value = "id") Long id,@RequestBody AttachUnitDto ptdto,UriComponentsBuilder uriCB){
        System.out.println("11111"+ptdto.getUnitAttachcode()+"222"+ptdto.getUnitAttachname());
        aus.modifyAttachUnit(id, ptdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/attachunit/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<AttachUnitDto>(ptdto,headers, HttpStatus.CREATED);  
    }
    
    /**
     * 查询职务（下拉框）
     * @author jcsi
     * @date 2016年7月11日
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value="/duty/dicts",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> findAllAttachUnit(){
        List<Map> map=aus.findAllAttachUnit();
        return map;
    }

    
}
