
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : PositionController.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年7月15日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月15日    DuPengXin   1.0
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

import com.yonyou.dms.common.domains.PO.basedata.MaintainWorkTypePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.repair.domains.DTO.basedata.RepairPositionDTO;
import com.yonyou.dms.repair.service.basedata.RepairPositionService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 工位定义
 * @author DuPengXin
 * @date 2016年7月15日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/repairpositions")
public class RepairPositionController extends BaseController{

    @Autowired
    private RepairPositionService positionservice;

    /**
     * 查询
     * @author DuPengXin
     * @date 2016年7月15日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPosition(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = positionservice.QueryPosition(queryParam);
        return pageInfoDto;
    }

    /**
     * 新增
     * @author DuPengXin
     * @date 2016年7月15日
     * @param positiondto
     * @param uriCB
     * @return
     */

    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<RepairPositionDTO> addPosition(@RequestBody RepairPositionDTO positiondto,UriComponentsBuilder uriCB){
        Long id=positionservice.addPosition(positiondto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/repairpositions/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<RepairPositionDTO>(positiondto,headers, HttpStatus.CREATED);  
    }

    /**
     * 修改
     * @author DuPengXin
     * @date 2016年7月15日
     * @param id
     * @param positiondto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<RepairPositionDTO> updatePosition(@PathVariable("id") Long id,@RequestBody RepairPositionDTO positiondto,UriComponentsBuilder uriCB) {
        positionservice.updatePosition(id,positiondto);

        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/repairpositions/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<RepairPositionDTO>(headers, HttpStatus.CREATED);  
    }

    /**
     * 根据ID进行查询
     * @author DuPengXin
     * @date 2016年7月15日
     * @param id
     * @return
     */

    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findById(@PathVariable Long id){
        MaintainWorkTypePO position= positionservice.findById(id);
        return position.toMap();
    }

    /**
     *查询维修工位  (下拉框) 
     * @author jcsi
     * @date 2016年7月11日
     * @return map
     */
    @RequestMapping(value="/employee/dicts",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> findAllRepairPosition(){
        List<Map> map=positionservice.findAllRepairPosition();
        return map;
    }
    
    /**
     * 派工工位下拉框
    * TODO description
    * @author rongzoujie
    * @date 2016年9月26日
    * @return
     */
    @RequestMapping(value="/employee/queryPosition",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> queryPosition(){
        return positionservice.queryPosition();
    }
}
