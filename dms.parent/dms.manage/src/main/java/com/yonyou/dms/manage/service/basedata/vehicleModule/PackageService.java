
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : ConfigurationService.java
*
* @Author : yll
*
* @Date : 2016年7月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月7日    yll    1.0
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
import com.yonyou.dms.manage.domains.DTO.basedata.vehicleModule.PackageDto;

/**
* 配件接口
* @author yll
* @date 2016年7月7日
*/

@SuppressWarnings("rawtypes")
public interface PackageService {


	public PageInfoDto queryConfiguration(Map<String, String> queryParam) throws ServiceBizException ;

	public Map<String,Object> getConfigurationById(String id) throws ServiceBizException;
	
	public Long addConfiguration(PackageDto configurationDto) throws ServiceBizException;
	
	public void modifyConfiguration(String ccode,PackageDto configurationDto) throws ServiceBizException;
	
	public void deleteConfigurationById(String bcode,String scode,String mcode,String ccode) throws ServiceBizException;
	
	public List<Map> queryConfiguration2(Map<String,String> queryParam) throws ServiceBizException ;
	
	public List<Map> queryConfigurationOEM(Map<String,String> queryParam) throws ServiceBizException ;
	
	public List<Map> queryPackageC(Map<String,String> queryParam) throws ServiceBizException ;
	
	public List<Map> queryPackageSelectModel(Map<String,String> queryParam) throws ServiceBizException ;
	
	public List<Map> queryConfisForExport(Map<String,String> queryParam) throws ServiceBizException;

}
