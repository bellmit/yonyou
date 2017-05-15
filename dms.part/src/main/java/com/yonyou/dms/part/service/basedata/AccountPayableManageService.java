
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : AccountPayableManageService.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月3日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月3日    dingchaoyu    1.0
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
* @author dingchaoyu
* @date 2017年5月3日
*/

@SuppressWarnings("rawtypes")
public interface AccountPayableManageService {
    
    public PageInfoDto retrieveByReceive(Map map) throws ServiceBizException;
    
    public List<Map> retrieveByReceiveExport(Map map) throws ServiceBizException;
    
    public PageInfoDto queryPartsBuyInfo(String id) throws ServiceBizException;
    
    public PageInfoDto queryPartAllocateInItem(String id) throws ServiceBizException;
    
    public PageInfoDto queryPartAllocateOutItem(String id) throws ServiceBizException;
    
    public PageInfoDto queryPartCustomer(Map map) throws ServiceBizException;
    
    public List<Map> queryPartCustomers() throws ServiceBizException;
    
    public List<Map> queryPartsBuyInfoExport(String id) throws ServiceBizException;
    
    public List<Map> queryPartAllocateInItemExport(String id) throws ServiceBizException;
    
    public List<Map> queryPartAllocateOutItemExport(String id) throws ServiceBizException;
    
    public void maintainPartPayUpdate(Map map) throws ServiceBizException;

}
