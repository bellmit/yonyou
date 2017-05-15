
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SalesOrderSubmitService.java
*
* @Author : Administrator
*
* @Date : 2017年2月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月14日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.ordermanage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.ordermanage.FinanceOrderDTO;
import com.yonyou.dms.retail.domains.DTO.ordermanage.FinanceShutOrderDTO;

/**
* TODO description
* @author Administrator
* @date 2017年2月14日
*/
@SuppressWarnings("rawtypes")
public interface SalesOrderSubmitService {
    public PageInfoDto qrySRSForFincAudit(Map<String, String> queryParam) throws ServiceBizException;
    
    public void auditSalesOrder(FinanceOrderDTO financeDto) throws ServiceBizException;
    
    public void auditSalesOrderShut(FinanceShutOrderDTO financeShutDTO) throws ServiceBizException;
    
    public void auditSalesOrderMoney(FinanceShutOrderDTO financeShutDTO) throws ServiceBizException;

}
