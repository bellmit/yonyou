
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : NewStockInService.java
*
* @Author : yll
*
* @Date : 2016年12月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月8日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.vehicle.service.stockManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.StockInOutDto;

/**
* 整车入库
* @author yll
* @date 2016年12月8日
*/

public interface NewStockInService {
    public void inbounds( StockInOutDto stockInOutDto) throws ServiceBizException;

    public void modifyStockIn(Long id,StockInOutDto stockInOutDTO)throws ServiceBizException;

    public PageInfoDto queryStockIn(Map<String, String> queryParam) throws ServiceBizException;

    public List<Map> exportStockInInfo(Map<String, String> queryParam) throws ServiceBizException;
    
    public Map<String, Object> queryStockInById(Long id) throws ServiceBizException;
    
    public List<Map> queryStockInByIds(String ids) throws ServiceBizException;
}
