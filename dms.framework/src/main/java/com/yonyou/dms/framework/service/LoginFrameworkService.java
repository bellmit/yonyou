/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : LoginFrameworkService.java
*
* @Author : zhangxc
*
* @Date : 2016年8月1日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月1日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.service;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 
* @author zhangxc
* @date 2016年8月1日
*/

public interface LoginFrameworkService {
    //确认是否可以登录
    boolean checkIsCanAccess(Long funcId,String requestUrl,String requestMethod) throws ServiceBizException;
}
