
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : PlatformService.java
*
* @Author : Administrator
*
* @Date : 2017年2月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月8日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.service.basedata.platform;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.platform.PlatformDto;

/**
* TODO description
* @author Administrator
* @date 2017年2月8日
*/

public interface PlatformService {
    
    public PageInfoDto queryPotentialCustomer(Map<String, String> queryParams)throws ServiceBizException;//客户信息
    
    @SuppressWarnings("rawtypes")
	public Map queryPlatformCount()throws ServiceBizException;//平台客户和订单数量
    
    public PageInfoDto querySalesOrder(Map<String, String> queryParams)throws ServiceBizException;//订单信息
    
    public void modifyExceptTimesRange(PlatformDto platformDto) throws ServiceBizException;
    
}
