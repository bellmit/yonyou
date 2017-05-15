
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SalesOrderCancelService.java
*
* @Author : LiGaoqi
*
* @Date : 2017年1月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月13日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.ordermanage;

import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.SalesOrderCancelDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author LiGaoqi
* @date 2017年1月13日
*/
public interface SalesOrderCancelService {
    public PageInfoDto querySalesOrdersCancel(Map<String, String> queryParam) throws ServiceBizException;
    
    public void cancelApply(SalesOrderCancelDTO salesOrderCancelDTO) throws ServiceBizException;
    
    public void cancelOrder(SalesOrderCancelDTO salesOrderCancelDTO) throws ServiceBizException;

}
