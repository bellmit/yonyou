
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : BookPartService.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月19日    dingchaoyu    1.0
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
import com.yonyou.dms.part.domains.DTO.basedata.BookPartDTO;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月19日
*/

public interface BookPartService {
    
    public PageInfoDto queryPartObligated(Map map) throws ServiceBizException;
    
    public PageInfoDto queryRepairOrderBy(Map map) throws ServiceBizException;
    
    public PageInfoDto queryPartObligatedItem(String id) throws ServiceBizException;

    public PageInfoDto queryStockInfo(Map map) throws ServiceBizException;
    
    public PageInfoDto queryPartInfo(String id,String ids) throws ServiceBizException; 
    
    public Map queryPartInfos(Map map) throws ServiceBizException; 
    
    public void performExecute(BookPartDTO bookPartDTO) throws ServiceBizException; 
    
    public void queryPart(Map map) throws ServiceBizException;
    
    public String performExecutes(BookPartDTO bookPartDTO) throws ServiceBizException; 
    
    public PageInfoDto queryPartsFromRepairOrder(String id) throws ServiceBizException; 

    public PageInfoDto QueryPartsReplace(String id) throws ServiceBizException;

	public List<Map> queryPartWorkshopItem(String roNo) throws ServiceBizException;

	public Map<String,Object> queryPartWorkshopDetail(String roNo, String storageCode, String partNo) throws ServiceBizException;

	public List<Map> queryPartInfoVehicle(String id, String ids) throws ServiceBizException;
}
