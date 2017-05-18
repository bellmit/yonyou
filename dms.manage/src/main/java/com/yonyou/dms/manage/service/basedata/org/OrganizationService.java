
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : OperateLogService.java
*
* @Author : rongzoujie
*
* @Date : 2016年7月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月14日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.manage.service.basedata.org;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.OrganizationDto;

public interface OrganizationService  {
    @SuppressWarnings("rawtypes")
	List<Map> getOrganization() throws ServiceBizException;
    @SuppressWarnings("rawtypes")
	List<Map> getIsValidOrganization() throws ServiceBizException;
    Map<String,Object> getOrgByCode(String orgCode) throws ServiceBizException;
    String addOrg(OrganizationDto orgDto) throws ServiceBizException;
    void modifyOrg(Integer id,OrganizationDto orgDto) throws ServiceBizException;
    void deleteOrgById(Long id) throws ServiceBizException;
    boolean checkChild(String orgCode) throws ServiceBizException;
    @SuppressWarnings("rawtypes")
	List<Map> getParents() throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	Map findByOrgCode(String employeeCode);
    
	@SuppressWarnings("rawtypes")
	List<Map> qrySalesOrdersDetial(Map<String, String> queryParam, String parentCode) throws ServiceBizException;
}
