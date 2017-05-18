
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerManagerService.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月27日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.customer.domains.DTO.customerManage.CustomerManageDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月27日
*/
@SuppressWarnings("rawtypes")
public interface CustomerManagerService {
    
    public PageInfoDto queryByFixspell(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto queryByFixspells(String id) throws ServiceBizException;
    
    public List<Map> queryByFixspellExports(String id) throws ServiceBizException;
    
    public List<Map> queryByFixspellExport(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto queryByNoOrSpellForPageQuery(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto queryByNoOrSpellForPageQuerys(String id) throws ServiceBizException;
    
    public List<Map> queryByNoOrSpellForPageQueryExports(String id) throws ServiceBizException;
    
    public List<Map> queryByNoOrSpellForPageQueryExport(Map<String, String> queryParam) throws ServiceBizException;
    
    public void addCustomerManageles(String id,CustomerManageDTO customerManageDTO) throws ServiceBizException;
    
    public void queryRepairOrderByDateStand(String id) throws ServiceBizException; 
    
    public void performExecute(String id) throws ServiceBizException;

}
