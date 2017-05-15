
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : RoleMenuActionServiceImpl.java
*
* @Author : yll
*
* @Date : 2016年8月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月9日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.manage.service.basedata.role;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 角色菜单操作权限service接口
* @author yll
* @date 2016年8月9日
*/
public interface RoleMenuActionService {
	
	public List<Map> remoteUrl(Long menuId) throws ServiceBizException;

	public void addOneAction(String oneAction,String roleId) throws ServiceBizException;
	
	public Map<String,String> queryRoleMenuAction(String menuId) throws ServiceBizException;
	
	public String[] findMenuAction(String roleId) throws ServiceBizException;
}
