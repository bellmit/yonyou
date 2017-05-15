
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : OrderPut.java
*
* @Author : zhongsw
*
* @Date : 2016年8月25日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月25日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.report.service.impl;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author zhongsw
* @date 2016年8月25日
*/

public interface OrderPutService {
    
    public PageInfoDto searchOrderPut(Map<String, String> param)throws ServiceBizException;
    
    public PageInfoDto searchOrderPut2(Long id)throws ServiceBizException;
    
    public List<Map> queryUsersForExport(Map<String,String> queryParam) throws ServiceBizException;
    
    public List<Map> queryUsersForExport2(Map<String,String> queryParam) throws ServiceBizException;
    
}
