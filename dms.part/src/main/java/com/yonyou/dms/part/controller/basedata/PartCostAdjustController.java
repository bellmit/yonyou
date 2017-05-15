
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartCostAdjustController.java
*
* @Author : jcsi
*
* @Date : 2016年7月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月15日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.controller.basedata;

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
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.part.domains.DTO.basedata.PartCostAdjustDto;
import com.yonyou.dms.part.service.basedata.PartCostAdjustService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
*配件成本价调整流水账
* @author jcsi
* @date 2016年7月15日
*/
@Controller
@TxnConn
@RequestMapping("/basedata/partcostadjusts")
public class PartCostAdjustController extends BaseController{

    @Autowired
    private PartCostAdjustService partCostAdjustService;
    
    @Autowired
    private CommonNoService commonNoService;
    
    /**
    * 查询
    * @author jcsi
    * @date 2016年7月18日
    * @param param
    * @return PageInfoDto
     */
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto search(@RequestParam Map<String, String> param){
        return  partCostAdjustService.search(param);
    }
    
    
    /**
    * 根据id查询
    * @author jcsi
    * @date 2016年7月21日
    * @param id
    * @return
     */
    @RequestMapping(value="/{PART_NO}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findStoreById(@PathVariable(value = "PART_NO") String id){
        return partCostAdjustService.findStoreById(id);
    }
    
    /**
    * 修改
    * @author jcsi
    * @date 2016年7月21日
    * @param id
    * @param pcaDto
    * @param uriCB
    * @return
     */
    @RequestMapping(value="/aa/{PART_NO}/{STORAGE_CODE}",method=RequestMethod.PUT)
    public ResponseEntity<PartCostAdjustDto> edit(@PathVariable("PART_NO") String id,@PathVariable("STORAGE_CODE") String ids,@RequestBody Map pcaDto,UriComponentsBuilder uriCB){
        partCostAdjustService.update(id,ids, pcaDto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/partcostadjusts/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<PartCostAdjustDto>(headers, HttpStatus.CREATED);  
    }
    
   
}
