
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : WorkerTypeController.java
*
* @Author : jcsi
*
* @Date : 2016年7月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月8日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.repair.controller.basedata;	
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.yonyou.dms.repair.domains.DTO.basedata.WorkerTypeDto;
import com.yonyou.dms.repair.service.basedata.WorkerTypeService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 工种定义
 * 
 * @author jcsi
 * @date 2016年7月1日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/workerTypes")
public class WorkerTypeController extends BaseController {

    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(WorkerTypeController.class);

    @Autowired
    private WorkerTypeService   workerTypeService;

    /**
     * 根据条件查询工种定义信息
     * 
     * @author jcsi
     * @date 2016年7月1日
     * @return PageInfoDto
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto getWorkerType(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = workerTypeService.queryWorkerType(queryParam);
        return pageInfoDto;
    }

    /**
     * 新增
     * 
     * @author jcsi
     * @date 2016年7月1日
     * @param workerType
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<WorkerTypeDto> addWorkerType(@RequestBody @Valid WorkerTypeDto workerTypeDto,
                                                       UriComponentsBuilder uriCB) {
        String id = workerTypeService.insertWOrkerType(workerTypeDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/workerTypes/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<WorkerTypeDto>(workerTypeDto, headers, HttpStatus.CREATED);
    }
 
    /**
     * 修改
     * 
     * @author jcsi
     * @date 2016年7月1日
     * @param id
     * @param workerType
     */
    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<WorkerTypeDto> updateWorkerType(@PathVariable String id,@RequestBody @Valid WorkerTypeDto workerTypeDto,UriComponentsBuilder uriCB){
        workerTypeService.updateWorkerType(id, workerTypeDto);
        
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/workerTypes/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<WorkerTypeDto>(headers, HttpStatus.CREATED);  
    }
    
    /**
     * 删除
     * 
     * @author jcsi
     * @date 2016年7月1日
     * @param id
     */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkerType(@PathVariable String id){
        workerTypeService.deleteWOrkerType(id);
    }
    
    /**
     * 根据id查找
     * 
     * @author jcsi
     * @date 2016年7月1日
     * @param id
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findById(@PathVariable String id){
        return workerTypeService.findById(id);
    }
    
    /**
     * 查询工种(下拉框显示)
    * @author jcsi
    * @date 2016年7月8日
    * @return map
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value="/employees/dicts",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> findAllWorkerType(){
        List<Map> map=workerTypeService.findAllWorkerType();
        return map;
    }
    
    
}
