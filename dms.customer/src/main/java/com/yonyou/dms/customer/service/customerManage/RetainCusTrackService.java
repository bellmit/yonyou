
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : RetainCusTrackService.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月26日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.OwnerCusDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TreatDTO;
import com.yonyou.dms.common.domains.DTO.customer.CustomerTrackingDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 保有客户跟进
 * 
 * @author zhanshiwei
 * @date 2016年8月26日
 */

public interface RetainCusTrackService {

    public PageInfoDto queryRetainCusTrackInfo(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto queryRetainCusTrackVin(Map<String, String> queryParam) throws ServiceBizException;

    public Map<String, Object> queryRetainCusTrackInfoByid(Long tracking_id) throws ServiceBizException;

    public List<Map> queryRetainCusTrackLink(String employeeNo) throws ServiceBizException;
    
    public Map<String, Object> queryRetainCusTrackInfoVin(String vin) throws ServiceBizException;

    public long addRetainCusTrackInfo(TreatDTO traDto) throws ServiceBizException;

    public long modifyRetainCusTrackInfo(Long tracking_id, TreatDTO traDto) throws ServiceBizException;

    public long deleteRetainCusTrackInfo(Long tracking_id) throws ServiceBizException;

    public void modifyReRetainCusTrack(CustomerTrackingDTO traDto) throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> queryOwnerCusByTreat(String customerNo, String vin, String dealerCode) throws ServiceBizException;
    
    public PageInfoDto queryRetainCusTrackInfoCampaign(String dealerCode) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryRetainCustrackforExport(Map<String, String> queryParam) throws ServiceBizException;
    
    public void modifySoldBy(CustomerTrackingDTO traDto) throws ServiceBizException;

}
