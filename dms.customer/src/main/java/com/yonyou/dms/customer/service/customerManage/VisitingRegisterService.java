
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : VisitingRegisterService.java
*
* @Author : Administrator
*
* @Date : 2016年12月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月28日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.VisitingRecordPO;
import com.yonyou.dms.customer.domains.DTO.customerManage.SalesPromotionDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.VisitingRecordDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author Administrator
* @date 2016年12月28日
*/

public interface VisitingRegisterService {
    public PageInfoDto queryVisitingRecordInfo(Map<String, String> queryParam) throws ServiceBizException;

    public Long addVisitingRecordInfo(VisitingRecordDTO visitDto) throws ServiceBizException;

    public VisitingRecordPO queryVisitingRecordInfoByid(Long id) throws ServiceBizException;

    public List<Map> queryVisitIntentInfoByParendId(Long id) throws ServiceBizException;

    public boolean checkoutMobile(String mobile, String cusno) throws ServiceBizException;

    public boolean checkoutMobileforVisits(String mobile, String id) throws ServiceBizException;

    public void modifyVisitingRecordInfo(Long id, VisitingRecordDTO visitDto) throws ServiceBizException;

    public List<Map> queryVisitingRecordforExport(Map<String, String> queryParam) throws ServiceBizException;

    public Long addSalesPromotionInfo(SalesPromotionDTO salesProDto) throws ServiceBizException;

    public void deleteVisitIntentInfo(Long id) throws ServiceBizException;
    
    public PageInfoDto queryCustomerInfoByid(Map<String, String> queryParam) throws ServiceBizException;//再分配查询客户信息
    
    public void modifySoldBy(VisitingRecordDTO visitDto) throws ServiceBizException;//再分配
    
    
    
    
}
