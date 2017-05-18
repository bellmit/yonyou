
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : RoleCtrlService.java
*
* @Author : yll
*
* @Date : 2016年8月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月8日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.service.basedata.role;

import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 角色受控权限
* @author yll
* @date 2016年8月8日
*/

public interface  RoleCtrlService {

	public Long addRoleCtrl(String RoleID ,String ctrlCode,Integer type) throws ServiceBizException;

	public void deleteMenuByRoleId(String RoleID) throws ServiceBizException;
	
	public Map<String, String> queryMenuCtrl(String roletId) throws ServiceBizException;
	
	

}
