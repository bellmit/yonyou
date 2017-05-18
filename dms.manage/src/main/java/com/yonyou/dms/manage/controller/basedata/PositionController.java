
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : PositionController.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年7月15日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月15日    ZhengHe    1.0
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
import com.yonyou.dms.manage.domains.DTO.basedata.PositionDTO;
import com.yonyou.dms.manage.domains.PO.basedata.PositionPo;
import com.yonyou.dms.manage.service.basedata.position.PositionService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 职务信息进行管理
 * @author ZhengHe
 * @date 2016年7月15日
 */

@Controller
@TxnConn
@RequestMapping("/basedata/positions")
public class PositionController extends BaseController{

    @Autowired
    private PositionService ps;

    /** 
     * 获取所有职务信息
     * @author ZhengHe
     * @date 2016年7月15日
     * @param queryParams
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto getPositions(@RequestParam Map<String, String> queryParams){
        return ps.queryPosition(queryParams);
    }
    
    /**
     * 新增职务
     * @author ZhengHe
     * @date 2016年7月18日
     * @param ptdto
     * @param uriCB
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PositionDTO> addPosition(@RequestBody PositionDTO ptdto,UriComponentsBuilder uriCB){
        Long id =ps.addPosition(ptdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/positions/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
    }

    /**
     * 
     * 根据id获取职务信息
     * @author ZhengHe
     * @date 2016年7月18日
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getPositionById(@PathVariable(value = "id") Long id){
        PositionPo ptPo=ps.getPositionById(id);
        return ptPo.toMap();
    }

    /**
     * 
     * 根据id修改职务信息
     * @author ZhengHe
     * @date 2016年7月18日
     * @param id
     * @param ptdto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<PositionDTO> ModifyPosition(@PathVariable(value = "id") Long id,@RequestBody PositionDTO ptdto,UriComponentsBuilder uriCB){
        ps.modifyPosition(id, ptdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/positions/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
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
    public List<Map> findAllPosition(){
        List<Map> map=ps.findAllPosition();
        return map;
    }
    

    
    
    
    /**
     * 根据ID 删除职务信息
     * 
     * @author zhangxc
     * @date 2016年12月15日
     * @param id
     * @param uriCB
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
        ps.deletePositionById(id);
    }
    
    
    
    
    
}
