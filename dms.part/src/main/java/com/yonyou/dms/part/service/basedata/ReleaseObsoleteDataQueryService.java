
/** 
*Copyright 2017 Yonyou Auto Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : ReleaseObsoleteDataQueryService.java
*
* @Author : Administrator
*
* @Date : 2017年4月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月19日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author Administrator
* @date 2017年4月19日
*/

public interface ReleaseObsoleteDataQueryService {

    public PageInfoDto queryReleaseObsoleteDataQuery(Map<String, String> queryParams) throws ServiceBizException;

    public List<Map> exportReleaseObsoleteDataQuery(Map<String, String> queryParam) throws ServiceBizException;

    public List<Map> queryStorageCode() throws ServiceBizException;
}
