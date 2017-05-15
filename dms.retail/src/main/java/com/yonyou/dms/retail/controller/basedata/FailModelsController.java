
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.retail
 *
 * @File name : FailModelsController.java
 *
 * @Author : yll
 *
 * @Date : 2016年7月5日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月5日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */
package com.yonyou.dms.retail.controller.basedata;

import java.util.Map;

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
import com.yonyou.dms.retail.domains.DTO.basedata.FailModelsDto;
import com.yonyou.dms.retail.domains.PO.basedata.FailModels;
import com.yonyou.dms.retail.service.basedata.FailModelsService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 简要描述：战败车型定义类
 * 通过此类对战败车型进行增删该查等操作
 * @author yll
 * @date 2016年6月30日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/failModels")
public class FailModelsController extends BaseController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(FailModelsController.class);

	@Autowired
	private FailModelsService failModelService;



	/**
	 * 
	 * 根据查询条件返回对应的战败车型数据
	 * restful,GET提交方式 获取全部战败车型信息
	 * @author yll
	 * @date 2016年6月30日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryFailModels(@RequestParam Map<String, String> queryParam) {
		PageInfoDto failModellist = failModelService.queryFailModel(queryParam);

		return failModellist;
	}

	/**
	 * 
	 *  restful,GET提交方式 获取单个战败车型信息
	 * 通过@PathVariable获取
	 * @author yll
	 * @date 2016年6月30日
	 * @param id 战败车型id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getFailModelsById(@PathVariable(value = "id") Long id) {
		FailModels failModel= failModelService.getFailModelById(id);
		return failModel.toMap();
	}


	/**
	 * 通过传入信息对战败车型信息进行新增操作
	 * restful,POST提交方式 添加新
	 * @author yll
	 * @date 2016年6月30日
	 * @param failModelDto 战败车型信息
	 * @param uriCB 
	 * @return 新增的战败车型信息
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<FailModelsDto> addFailModel(@RequestBody FailModelsDto failModelDto,UriComponentsBuilder uriCB) {
		Long id = failModelService.addFailModel(failModelDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/basedata/failModels/{id}").buildAndExpand(id).toUriString());  
		return new ResponseEntity<FailModelsDto>(failModelDto,headers, HttpStatus.CREATED);  

	}

	/**
	 * 
	 * 修改战败车型信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @param failModelsDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<FailModelsDto> ModifyFailModels(@PathVariable("id") Long id,@RequestBody FailModelsDto failModelsDto,UriComponentsBuilder uriCB) {
		failModelService.modifyFailModel(id, failModelsDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/basedata/failModels/{id}").buildAndExpand(id).toUriString());  
		return new ResponseEntity<FailModelsDto>(headers, HttpStatus.CREATED);
	}

	/**
	 * 
	 * 根据id删除战败车型信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @param uriCB
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteFailModel(@PathVariable("id") Long id) {
		failModelService.deleteFailModelById(id);
	}

}
