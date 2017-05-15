
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : LendStuffService.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月10日    dingchaoyu    1.0
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
import com.yonyou.dms.part.domains.DTO.basedata.LendStuffDTO;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月10日
*/
@SuppressWarnings("rawtypes")
public interface LendStuffService {
    
    public List<Map> queryRepairType() throws ServiceBizException;
    
    public PageInfoDto query(Map map) throws ServiceBizException;
    
    public PageInfoDto queryRepair(String id) throws ServiceBizException;
    
    public void partWorkshopItem(LendStuffDTO dto) throws ServiceBizException;
    
    public PageInfoDto queryStock(Map dto) throws ServiceBizException;
    
    public String queryStocks(LendStuffDTO dto) throws ServiceBizException;
}
