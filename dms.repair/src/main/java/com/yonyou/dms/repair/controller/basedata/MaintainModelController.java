
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : MaintainModelController.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年7月12日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月12日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.controller.basedata;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.repair.domains.DTO.basedata.MaintainModelDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.ModelGroupItemDTO;
import com.yonyou.dms.repair.domains.PO.basedata.MaintainModelPO;
import com.yonyou.dms.repair.service.basedata.MaintainModelService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 车型定义
 * @author DuPengXin
 * @date 2016年7月12日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/modelgroups")
public class MaintainModelController extends BaseController{

    @Autowired
    private MaintainModelService modelservice;

    @RequestMapping(value = "/getModelSingleName",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getModelSingleName(){
        return modelservice.getModelName();
    }

    /**
     * 
     * 获取项目车型组名称多选下来框
     * @author rongzoujie
     * @date 2016年8月10日
     * @return
     */
    @RequestMapping(value = "/getMaintainModel/dicts",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getModelName(){
        List<Map> modelList = modelservice.getModelName();
        List<Map> modeListNameCode = new ArrayList<Map>();
        for(int i=0;i<modelList.size();i++){
            Map<String,String>  modelNameCodeMap = new HashMap<String,String>();
            modelNameCodeMap.put("MODEL_LABOUR_NAME", modelList.get(i).get("MODEL_LABOUR_NAME")+"/"+modelList.get(i).get("MODEL_LABOUR_CODE"));
            modelNameCodeMap.put("MODEL_LABOUR_CODE", modelList.get(i).get("MODEL_LABOUR_CODE").toString());
            modeListNameCode.add(modelNameCodeMap);
        }

        return modeListNameCode;
    }

    /**
     * 查询
     * @author DuPengXin
     * @date 2016年7月12日
     * @param queryParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryModel(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = modelservice.QueryModel(queryParam);
        return pageInfoDto;
    }
    
    @RequestMapping(value = "/{modelName}/getModelCode", method = RequestMethod.GET)
    @ResponseBody
    public String getModelCode(@PathVariable("id") Long id){
        return null;
    } 
    

    /**
     * 新增
     * @author DuPengXin
     * @date 2016年7月12日
     * @param modeldto
     * @param uriCB
     * @return
     */

    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<MaintainModelDTO> addModel(@RequestBody MaintainModelDTO modeldto,UriComponentsBuilder uriCB){
        Long id=modelservice.addModel(modeldto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/modelgroups/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<MaintainModelDTO>(modeldto,headers, HttpStatus.CREATED);  
    }

    /**
     * 子表新增
     * @author DuPengXin
     * @date 2016年8月3日
     * @param groupdto
     * @param uriCB
     * @return
     */

    @RequestMapping(value="/{id}/groupitems",method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ModelGroupItemDTO> addGroup(@RequestBody ModelGroupItemDTO groupdto,UriComponentsBuilder uriCB){
        Long id=modelservice.addGroup(groupdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/modelgroups/{id}/groupitems").buildAndExpand(id).toUriString());  
        return new ResponseEntity<ModelGroupItemDTO>(groupdto,headers, HttpStatus.CREATED);  
    }
    /**
     * 修改
     * @author DuPengXin
     * @date 2016年7月12日
     * @param id
     * @param modeldto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MaintainModelDTO> updateModel(@PathVariable("id") Long id,@RequestBody MaintainModelDTO modeldto,UriComponentsBuilder uriCB) {
        modelservice.updateModel(id,modeldto);

        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/modelgroups/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<MaintainModelDTO>(headers, HttpStatus.CREATED);  
    }

    /**
     * 子表删除
     * @author DuPengXin
     * @date 2016年8月3日
     * @param id
     * @param uriCB
     */

    @RequestMapping(value = "/{id}/groupitems", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroupById(@PathVariable("id") Long id,UriComponentsBuilder uriCB) {
        modelservice.deleteGroupById(id);
    }
    /**
     * 根据ID进行查询
     * @author DuPengXin
     * @date 2016年7月12日
     * @param id
     * @return
     */

    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findById(@PathVariable Long id){
        MaintainModelPO model= modelservice.findById(id);
        return model.toMap();
    }


    /**
     * 根据ID查询详细信息
     * @author DuPengXin
     * @date 2016年7月22日
     * @param id
     * @return
     */

    @RequestMapping(value = "/{id}/groupitems", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryGroupItem(@PathVariable("id") Long id) {
        PageInfoDto groupitemList = modelservice.queryGroupItem(id);
        return groupitemList;
    }
    
    /**
    * 根据modeCode查询modelGroupCode
    * @author rongzoujie
    * @date 2016年11月11日
    * @param modeCode
    * @return
     */
    @RequestMapping(value = "/{modelCode}/getModelGroupCode", method = RequestMethod.GET)
    @ResponseBody
    public String getModelGroupCode(@PathVariable("modelCode") String modelCode){
        return modelservice.getModelGroupCode(modelCode);
    }
    
}
