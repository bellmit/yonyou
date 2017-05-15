/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.web
*
* @File name : MenuService.java
*
* @Author : rongzoujie
*
* @Date : 2016年7月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月6日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.web.service.login;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;

public interface MenuService {
	
    List<Map> queryMenu() throws ServiceBizException;
    
    List<Map> queryHandles() throws ServiceBizException;
    
    List<Map> querySystemParams() throws ServiceBizException;
    
    boolean checkIsCanAccess(Long funcId,String requestUrl) throws ServiceBizException;
}
