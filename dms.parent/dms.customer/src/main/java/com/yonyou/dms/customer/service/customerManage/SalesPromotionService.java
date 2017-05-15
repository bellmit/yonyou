
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : SalesPromotionService.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月12日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.TtSalesPromotionPlanDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.SalesPromotionDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 潜客跟进
 * 
 * @author LGQ
 * @date 2016年1月1日
 */

public interface SalesPromotionService {

    public PageInfoDto querySalesPromotionInfo(Map<String, String> queryParam,int flag) throws ServiceBizException;
    
    public PageInfoDto queryBigCustomerHistoryIntent(String id) throws ServiceBizException;

    public Long addSalesPromotionInfo(TtSalesPromotionPlanDTO salesProDto) throws ServiceBizException;
    
    public void updateSalesPromotionInfo(long id, TtSalesPromotionPlanDTO salesProDto) throws ServiceBizException;
    
    public void modifySalesPromotionInfo(Long id, SalesPromotionDTO salesProDto) throws ServiceBizException;

    public Map<String, Object> querySalesPromotionInfo(Long id) throws ServiceBizException;
    public List<Map> queryFollowHistoryList(String id) throws ServiceBizException;
    public void deleteSalesPromotionInfoByid(Long id) throws ServiceBizException;
    
    public void modifySoldBy(TtSalesPromotionPlanDTO salesProDto) throws ServiceBizException;
    
    public void auditSalesPromotionPlan(TtSalesPromotionPlanDTO salesProDto) throws ServiceBizException;

    public List<Map> querySalesPromotionAllByid(Long id) throws ServiceBizException;

    public void modifySalesPromotionForConsultant(SalesPromotionDTO salesProDto) throws ServiceBizException;

    public List<Map> queryDefeatModel(Long id) throws ServiceBizException;
    
    public List<Map> quickQuery() throws ServiceBizException;
    
    public List<Map> queryPotentialfollowforExport(Map<String, String> queryParam,int flag) throws ServiceBizException;
    
    public Long addSalesPromotion(String itemId,TtSalesPromotionPlanDTO salesProDto) throws ServiceBizException;


}
