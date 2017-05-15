
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.web
*
* @File name : DictController.java
*
* @Author : rongzoujie
*
* @Date : 2016年7月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月13日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.web.controller.basedata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.domains.DTO.baseData.DictDto;
import com.yonyou.dms.framework.domains.DTO.baseData.RegionDto;
import com.yonyou.dms.framework.service.cache.CacheService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/basedata/dicts")
public class DictController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DictController.class);
	
	@Resource(name="DictCache")
	CacheService<List<DictDto>> dictCacheSerivce;
	
	@Resource(name="RegionCache")
    CacheService<RegionDto> regionCacheService;
	
	/**
	 * 查询所有数据字典
	* TODO description
	* @author rongzoujie
	* @date 2016年7月4日
	* @return Map<String, Map<String,List<DictDto>>>
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryDicts(){
		Map<String, Object> dict = new HashMap<>();
		Map<?,RegionDto> regionList = regionCacheService.getAllData();
		Map<?, List<DictDto>> dictList = dictCacheSerivce.getAllData();
		dict.put("dict", dictList);
		dict.put("region", regionList);
		return dict;
	}
}
