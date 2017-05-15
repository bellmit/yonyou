
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : LabourGroupContoller.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年8月4日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月4日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.controller.basedata;

import java.util.ArrayList;
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
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.LabourGroupDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.LabourGroupTreeDto;
import com.yonyou.dms.repair.domains.DTO.basedata.LabourSubgroupDTO;
import com.yonyou.dms.repair.domains.PO.basedata.LabourGroupPO;
import com.yonyou.dms.repair.service.basedata.LabourGroupService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 维修项目分类
 * @author DuPengXin
 * @date 2016年8月4日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/labourgroups")
@SuppressWarnings("rawtypes")
public class LabourGroupContoller extends BaseController {

    @Autowired
    private LabourGroupService groupservice;

	@RequestMapping(value = "/getGroupList/{mainGroupName}",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> getSubGroupList(@PathVariable String mainGroupName){
        return groupservice.subGroupList(mainGroupName);
    }
    
    @RequestMapping(value = "/getGroupList",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> getMainGroupList(){
        return groupservice.getGroupList();
    }

    /**
     * 主分类下拉框
     * @author rongzoujie
     * @date 2016年8月10日
     * @return
     */
    @RequestMapping(value = "/getGroupList/dicts/items", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getGroupList(){
        return groupservice.getGroupList();
    }
    /**
     * 
     * 生成分类树
     * @author rongzoujie
     * @date 2016年8月9日
     * @return
     */
    @RequestMapping(value = "/getLabourGroup/trees", method = RequestMethod.GET)
    @ResponseBody
    public List<LabourGroupTreeDto> getLabourGroupTree(){
        Map<String,List<Map>> labourAllMap = groupservice.queryAlllabourGroup();
        List<LabourGroupTreeDto> labourgroupTree = new ArrayList<LabourGroupTreeDto>();
        List<Map> primaryGroupList = labourAllMap.get("primary");
        List<Map> secondGroupList = labourAllMap.get("second");

        for(int i=0;i<primaryGroupList.size();i++){
            LabourGroupTreeDto labourGroupTreeDto = new LabourGroupTreeDto();
            labourGroupTreeDto.setParent("#");
            labourGroupTreeDto.setText((String)primaryGroupList.get(i).get("MAIN_GROUP_CODE").toString()+" "+(String)primaryGroupList.get(i).get("MAIN_GROUP_NAME").toString());
            labourgroupTree.add(labourGroupTreeDto);
        }

        for(int i=0;i<secondGroupList.size();i++){
            LabourGroupTreeDto labourGroupTreeDto = new LabourGroupTreeDto();
            if(!StringUtils.isNullOrEmpty(secondGroupList.get(i).get("MAIN_GROUP_CODE"))){
                labourGroupTreeDto.setParent((String)secondGroupList.get(i).get("MAIN_GROUP_CODE").toString());
            }else{
                labourGroupTreeDto.setParent("1");
            }
            labourGroupTreeDto.setText((String)secondGroupList.get(i).get("SUB_GROUP_CODE").toString()+" "+(String)secondGroupList.get(i).get("SUB_GROUP_NAME").toString());
            labourgroupTree.add(labourGroupTreeDto);
        }
        return labourgroupTree;
    }

    /**
     * 查询
     * @author DuPengXin
     * @date 2016年8月4日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryLabourGroup(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = groupservice.QueryLabourGroup(queryParam);
        return pageInfoDto;
    }

    /**
     * 点击主表显示子表信息
     * @author DuPengXin
     * @date 2016年8月4日
     * @param id
     * @return
     */

    @RequestMapping(value = "/{id}/subgroups", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryLabourSubgroup(@PathVariable("id") String id) {
        PageInfoDto groupitemList = groupservice.queryLabourSubgroup(id);
        return groupitemList;
    }

    /**
     * 新增
     * @author DuPengXin
     * @date 2016年8月4日
     * @param labourgroupdto
     * @param uriCB
     * @return
     */

    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<LabourGroupDTO> addModel(@RequestBody LabourGroupDTO labourgroupdto,UriComponentsBuilder uriCB){
        String id=groupservice.addLabourGroup(labourgroupdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/labourgroups/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<LabourGroupDTO>(labourgroupdto,headers, HttpStatus.CREATED);  
    }

    /**
     * 子表新增
     * @author DuPengXin
     * @date 2016年8月4日
     * @param groupdto
     * @param uriCB
     * @return
     */

    @RequestMapping(value="/{id}/subgroups",method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<LabourSubgroupDTO> addGroup(@RequestBody LabourSubgroupDTO laboursubgroupdto,UriComponentsBuilder uriCB){
    	String id=groupservice.addLabourSubgroup(laboursubgroupdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/labourgroups/{id}/subgroups").buildAndExpand(id).toUriString());  
        return new ResponseEntity<LabourSubgroupDTO>(laboursubgroupdto,headers, HttpStatus.CREATED);  
    }

    /**
     * 修改
     * @author DuPengXin
     * @date 2016年8月4日
     * @param id
     * @param labourgroupdto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<LabourGroupDTO> updateLabourGroup(@PathVariable("id") String id,@RequestBody LabourGroupDTO labourgroupdto,UriComponentsBuilder uriCB) {
        groupservice.updateLabourGroup(id,labourgroupdto);

        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/labourgroups/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<LabourGroupDTO>(headers, HttpStatus.CREATED);  
    }

    /**
     * 子表修改
     * @author DuPengXin
     * @date 2016年8月4日
     * @param id
     * @param laboursubgroupdto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{id}/subgroups", method = RequestMethod.PUT)
    public ResponseEntity<LabourSubgroupDTO> updateLabourSubgroup(@PathVariable("id") String id,@RequestBody LabourSubgroupDTO laboursubgroupdto,UriComponentsBuilder uriCB) {
        groupservice.updateLabourSubgroup(id,laboursubgroupdto);

        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/labourgroups/{id}/subgroups").buildAndExpand(id).toUriString());  
        return new ResponseEntity<LabourSubgroupDTO>(headers, HttpStatus.CREATED);  
    }

    /**
     * 根据ID进行查询
     * @author DuPengXin
     * @date 2016年8月4日
     * @param id
     * @return
     */

    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findById(@PathVariable String id){
        LabourGroupPO labourgroup= groupservice.findById(id);
        return labourgroup.toMap();
    }

    /**
     * 根据ID查询子表
     * @author DuPengXin
     * @date 2016年8月8日
     * @param id
     * @return
     */

    @RequestMapping(value="/{id}/subgroupsinfo",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findByIditem(@PathVariable String id){ 
        Map<String, Object> laboursubgroup= groupservice.findByIditem(id);
        return laboursubgroup;
    }
}
