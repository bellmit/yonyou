
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : ModelService.java
 *
 * @Author : yll
 *
 * @Date : 2016年7月6日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月6日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.service.basedata.vehicleModule;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.vehicleModule.ModelDto;

/**
 * 车型的service接口
 * @author yll
 * @date 2016年7月6日
 */

@SuppressWarnings("rawtypes")
public interface ModelService {

	public PageInfoDto queryModel(Map<String, String> queryParam) throws ServiceBizException;

	public Map<String,Object> getModelById(String mcode,String scode,String bcode) throws ServiceBizException;

	public Long addModel(ModelDto modelDto) throws ServiceBizException;

	public void modifyModel(String mcode,String scode,String bcode,ModelDto modelDto) throws ServiceBizException;

	public void deleteModelById(String mcode,String scode,String bcode) throws ServiceBizException;

	public List<Map> queryModel2(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> queryModelOEM(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> queryModelC(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> queryModelS(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> queryModelCSr(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> queryLabour(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> queryPrice(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> selectModel(Map<String, String> queryParam) throws ServiceBizException;//车型下拉
	
	public List<Map> selectModelSr(Map<String, String> queryParam) throws ServiceBizException;//车型下拉

	public String selectModelIdByCode(String modelCode);

	public List<Map> queryModelsForExport(Map<String,String> queryParam) throws ServiceBizException;
}
