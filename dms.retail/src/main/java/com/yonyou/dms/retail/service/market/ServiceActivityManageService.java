
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : ServiceActivityManageService.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月16日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.market;

import java.util.Map;


import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.market.ServiceActivityManageDTO;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月16日
*/
@SuppressWarnings("rawtypes")
public interface ServiceActivityManageService {
    
    public PageInfoDto query(Map map) throws ServiceBizException;
    
    public Map<String,Object> querys(String id) throws ServiceBizException;
    
    public String save(ServiceActivityManageDTO dto) throws ServiceBizException;
    
}
