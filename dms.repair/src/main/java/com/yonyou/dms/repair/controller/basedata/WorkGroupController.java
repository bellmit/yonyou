
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : WorkGroupController.java
*
* @Author : xukl
*
* @Date : 2016年6月30日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年6月30日    xukl    1.0
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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.repair.domains.DTO.basedata.WorkGroupDto;
import com.yonyou.dms.repair.domains.PO.basedata.WorkGroupPO;
import com.yonyou.dms.repair.service.basedata.WorkGroupService;
import com.yonyou.dms.repair.service.basedata.WorkerTypeService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
 *简要描述：班组定义控制类
* 通过此类对班组信息进行增删改查操作
* @author xukl
* @date 2016年7月1日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/workgroups")
public class WorkGroupController extends BaseController{

	@Autowired
	private WorkGroupService workGroupService;

	/**
	* 简要描述:根据条件查询班组信息
	* @author xukl
	* @date 2016年6月30日
	* @param queryParam 
	* @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryWorkGroups(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = workGroupService.queryWorkGroups(queryParam);
		return pageInfoDto;
	}
	/**
	* 简单描述：通过id查询班组信息
	* @author xukl
	* @date 2016年6月30日
	* @param id 班组定义id
	* @return 班组信息
	*/
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getWorkGroupById(@PathVariable(value = "id") String id) {
        Map workGroup = workGroupService.getWorkGroupById(id);
		return workGroup;
	}
	/**
	* 通过传入信息对班组信息进行新增操作
	* @author xukl
	* @date 2016年6月30日
	* @param workGroupDto
	* @param uriCB
	* @return 新增的班组信息
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<WorkGroupDto> addWorkGroup(@RequestBody @Valid WorkGroupDto workGroupDto,UriComponentsBuilder uriCB) {
	      Map<String,String> queryParam = new HashMap<String,String>();
	      List<Map> list =  workGroupService.getWorkerGroupCode(workGroupDto.getWorkerType());
	      workGroupDto.setWorkerTypeCode(list.get(0).get("WORKER_TYPE_CODE").toString());
	      String id = workGroupService.addWorkGroup(workGroupDto);
		 MultiValueMap<String, String> headers = new HttpHeaders();
	     headers.set("Location", uriCB.path("/basedata/workgroups/{id}").buildAndExpand(id).toUriString());
	     return new ResponseEntity<WorkGroupDto>(workGroupDto, headers, HttpStatus.CREATED);

	}
	/**
	* 班组定义修改
	* @author xukl
	* @date 2016年6月30日
	* @param workgroup_idw
	* @param workGroupDto
	* @param uriCB
	* @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<WorkGroupDto> ModifyWorkGroup(@PathVariable("id") String workgroup_id,@RequestBody @Valid WorkGroupDto workGroupDto,UriComponentsBuilder uriCB) {
		List<Map> list =  workGroupService.getWorkerGroupCode(workGroupDto.getWorkerType());
	    workGroupDto.setWorkerTypeCode(list.get(0).get("WORKER_TYPE_CODE").toString());
		workGroupService.modifyWorkGroup(workgroup_id,workGroupDto);
		MultiValueMap<String, String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/workgroups/{workgroup_id}").buildAndExpand(workgroup_id).toUriString());  
		return new ResponseEntity<WorkGroupDto>(headers, HttpStatus.CREATED);  
	}
	
	/**
	* 班组下拉框
	* @author jcsi
	* @date 2016年10月11日
	* @return
	 */
	@RequestMapping(value="/dicts/select",method=RequestMethod.GET)
	@ResponseBody
	public List<Map> selectWorkGroupDicts(){
	    return workGroupService.selectWorkGroupDicts();
	}
}