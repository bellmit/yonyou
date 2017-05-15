
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : HasActivityVehicleQueryService.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月15日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.market;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月15日
*/
@SuppressWarnings("rawtypes")
public interface HasActivityVehicleQueryService {
    
    public PageInfoDto query(Map map) throws ServiceBizException;
    
    public List<Map> queryExport(Map map) throws ServiceBizException;
    
    public PageInfoDto queryDetail(Map map) throws ServiceBizException;
    
    public List<Map> queryDetailExport(Map map) throws ServiceBizException;
    
    public List<Map> queryOwnerNoByid(String id) throws ServiceBizException;
    
}
