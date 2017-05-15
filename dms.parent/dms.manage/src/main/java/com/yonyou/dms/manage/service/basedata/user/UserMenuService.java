
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : UserMenuService.java
*
* @Author : yll
*
* @Date : 2016年8月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月19日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.service.basedata.user;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 用户菜单
* @author yll
* @date 2016年8月19日
*/

public interface UserMenuService {

	public Long addUserMenu(String UserID ,String MenuID) throws ServiceBizException;

	public void deleteMenuByUserId(String id) throws ServiceBizException;
	
	public boolean findMenu(String UserId,String MenuID);
	
	public boolean parentNode(String MenuID);
}
