/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : WorkerTypeService.java
*
* @Author : jcsi
*
* @Date : 2016年7月1日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月1日    jcsi    1.0
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
import com.yonyou.dms.repair.domains.DTO.basedata.WorkerTypeDto;

/**
* 工种定义 接口
* @author jcsi
* @date 2016年7月29日
 */

@SuppressWarnings("rawtypes")
public interface WorkerTypeService {
	
	
	public PageInfoDto queryWorkerType(Map<String,String> queryParam)throws ServiceBizException;
	
	public String insertWOrkerType(WorkerTypeDto workerTypeDto)throws ServiceBizException;
	
	public void updateWorkerType(String id,WorkerTypeDto workerTypeDto)throws ServiceBizException;
	
	public void deleteWOrkerType(String id)throws ServiceBizException;
	
	public Map findById(String id)throws ServiceBizException;
	
	public List<Map> findAllWorkerType()throws ServiceBizException;

}
