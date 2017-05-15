
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : VisitingRecordService.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月31日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月31日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.PotentialCusDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtSalesPromotionPlanDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.SalesPromotionDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.VisitRecordDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.VisitingRecordDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 展厅接待
 * 
 * @author zhanshiwei
 * @date 2016年8月31日
 */

public interface VisitingRecordService {

    public PageInfoDto queryVisitingRecordInfo(Map<String, String> queryParam) throws ServiceBizException;

    public void addVisitingRecordInfo(VisitingRecordDTO visitDto) throws ServiceBizException;

    public Map<String,Object> queryVisitingRecordInfoByid(Long id) throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> queryVisitIntentInfoByParendId(Long id) throws ServiceBizException;

    public boolean checkoutMobile(String mobile, String cusno) throws ServiceBizException;

    public boolean checkoutMobileforVisits(String mobile, String id) throws ServiceBizException;

    public void modifyVisitingRecordInfo(Long id, VisitingRecordDTO visitDto) throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> queryVisitingRecordforExport(Map<String, String> queryParam) throws ServiceBizException;

    public Long addSalesPromotionInfo(SalesPromotionDTO salesProDto) throws ServiceBizException;

    public void deleteVisitIntentInfo(Long id) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryContactor(Map<String, String> queryParam) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryIntent(String id) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryAddIntent(Map<String, String> queryParam) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryMediaDetail(Map<String, String> queryParam) throws ServiceBizException;
    
    //根据联系手机，查询联系人信息
    public Map<String,Object> queryMobileInfoByid(String mobile) throws ServiceBizException;
    
    //再分配
    public void modifySoldBy(VisitRecordDTO visitDto) throws ServiceBizException;//再分配
    
    //新增时可修改来访时间（受控权限）
    //public Map<String, Object> queryVisitTime(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto queryRedistributionInfo(Map<String, String> queryParam) throws ServiceBizException;
    
    //建档、接待时带出客户相关信息
    @SuppressWarnings("rawtypes")
	public List<Map> queryPotentialCusInfoByid(String id) throws ServiceBizException;
    
    //接待时查询意向
    @SuppressWarnings("rawtypes")
	public List<Map> queryCusIntent(String id) throws ServiceBizException;  
    
    public String addPotentialCusInfo(String id,PotentialCusDTO potentialCusDto, String customerNo) throws ServiceBizException;
    
    //新增时校验已建档手机号
    public Map<String,Object> queryContactorMobile(String id) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryCusIntent1(String id) throws ServiceBizException;
    
}
