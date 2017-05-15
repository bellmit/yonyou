
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : SalesPartService.java
*
* @Author : jcsi
*
* @Date : 2016年8月4日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月4日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.stockmanage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.commonAS.domains.PO.basedata.PartSalesPo;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartSalesDto;

/**
* 销售出库  接口
* @author jcsi
* @date 2016年8月4日
*/

public interface PartSalesService {
    
    public PageInfoDto search(Map<String, String> param)throws ServiceBizException;
    
    public void deleteById(Long id)throws ServiceBizException;
    
    public PartSalesPo addSalesAndItem(String salesPartNo,PartSalesDto spDto)throws ServiceBizException;

    public void editSalesAndItem(Long salesId,PartSalesDto spDto)throws ServiceBizException;
    
    public Map findSalesById(Long id)throws ServiceBizException;
   
    public List<Map> findSalesItemByPartId(Long id)throws ServiceBizException;
    
    public void updateStatusById(Long id)throws ServiceBizException;
    
    public PageInfoDto searchSalesReturn(Long id)throws ServiceBizException;
    
    public void salesReturnSub(Long id,PartSalesDto dto);
    
    public void updateOrderAmount(Long salesId,Map<String, Object> resultAmount);
    
    public PageInfoDto searchItemByRoNo(Long roId)throws ServiceBizException;
    
    public PageInfoDto findPartSales(Map<String, String> param)throws ServiceBizException;
    
    public List<Map> findBySalesNo(Map<String,String> param)throws ServiceBizException;
    
    public List<Map> searchSalesItemByRoId(Long roId)throws ServiceBizException;

	public List<Map> getPartSalesPrint(Long id)throws ServiceBizException;
}
