
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : BigCustomerManageRepayService.java
*
* @Author : Administrator
*
* @Date : 2017年1月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月18日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.BigCustomerRepayDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author Administrator
* @date 2017年1月18日
*/

public interface BigCustomerManageRepayService {
    public PageInfoDto queryBigCustomer(Map<String, String> queryParam) throws ServiceBizException;
    public PageInfoDto queryBigCustomerWs(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto queryBigCustomerWsCar(String id) throws ServiceBizException; 
    public PageInfoDto querySales(Map<String, String> queryParam,String wsAppType,String firstsubmittime) throws ServiceBizException;
    
    public Map<String, Object> employeSaveBeforeEvent(Map<String, String> queryParam,String brandCode,String seriesCode,String wsType) throws ServiceBizException;
    public Map<String, Object> employeSaveBeforeEventAmount(Map<String, String> queryParam) throws ServiceBizException;
    
    public String addBiGCusRepayInfo(BigCustomerRepayDTO BigCustomerDto) throws ServiceBizException;
    
    public PageInfoDto queryBigCustomerWsCarbyWsNo(Map<String, String> queryParam,String wsNo) throws ServiceBizException;
    public List<Map> queryOwnerCusBywsNo(String wsNo) throws ServiceBizException;
    public String modifyBiGCusInfo(BigCustomerRepayDTO BigCustomerDto,String wsNo) throws ServiceBizException;
    
    public String CheckStatus(String id) throws ServiceBizException;
    public String CheckStatus1(String id) throws ServiceBizException;
    
    public String uploanBigCustomer(String wsNo) throws ServiceBizException;
    public List<Map> CheckDateDetail(String id) throws ServiceBizException;
    
}
