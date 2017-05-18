
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : RoleMenuServiceImpl.java
*
* @Author : yll
*
* @Date : 2016年8月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月7日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.manage.service.basedata.role;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 角色菜单接口
* @author yll
* @date 2016年8月7日
*/
public interface RoleMenuService {
	public Long addRoleMenu(String RoleID ,String MenuID) throws ServiceBizException;

	public void deleteMenuByRoleId(String id) throws ServiceBizException;
	
	public boolean findMenu(String roleId,String MenuID);
	
	public boolean parentNode(String MenuID);
}
