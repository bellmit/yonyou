
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : WorkGroupService.java
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
package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.WorkGroupDto;
import com.yonyou.dms.repair.domains.PO.basedata.WorkGroupPO;


/**
* WorkGroupService
* @author xukl
* @date 2016年8月2日
*/
	
public interface WorkGroupService {

	public PageInfoDto queryWorkGroups(Map<String,String> queryParam) throws ServiceBizException;
	public Map getWorkGroupById(String id) throws ServiceBizException;
	public String addWorkGroup(WorkGroupDto user) throws ServiceBizException;
	public void modifyWorkGroup(String id,WorkGroupDto user) throws ServiceBizException;
	public List<Map> selectWorkGroupDicts();
	public List<Map> getWorkerGroupCode(String workerType) throws ServiceBizException;
}
