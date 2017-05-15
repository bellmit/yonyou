
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SalessynthesisService.java
*
* @Author : zhongsw
*
* @Date : 2016年10月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月8日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.ordermanage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO;

/**
* 销售综合查询接口
* @author zhongsw
* @date 2016年10月8日
*/

public interface SalessynthesisService {
    
    public PageInfoDto searchsalesSynthesis(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto searchAuditing(String id) throws ServiceBizException;
    
    public Map<String, Object> queryCustomerInfoById(Long id) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryCustomerInfoByParendId(Long id) throws ServiceBizException;
    
    public Map<String, Object> queryorderInfoById(Long id) throws ServiceBizException;
    
    public void modifySalesOrderInfo(SalesOrderDTO salesOrderDto) throws ServiceBizException;//订单解锁
    
    @SuppressWarnings("rawtypes")
	public List<Map> querySalesOrderInfoExport(Map<String, String> queryParam) throws ServiceBizException;//导出excel
    
    @SuppressWarnings("rawtypes")
	public Map getSalesOrderById(String id) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public Map getInsuranceById(String id) throws ServiceBizException;//保险详细
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryPotentialCusInfoByid(String id) throws ServiceBizException;//客户卡打印
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryMenuAction() throws ServiceBizException;//客户卡打印

}
