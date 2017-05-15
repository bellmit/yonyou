
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : PrecontractArrangeService.java
*
* @Author : Administrator
*
* @Date : 2017年3月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月29日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.Precontract;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO 客户预约安排
* @author zhl
* @date 2017年4月6日
*/

public interface PrecontractArrangeService {

    public PageInfoDto QueryChooseVin(Map<String, String> queryParam)throws ServiceBizException;

    String findBookingRecord(String vin, String license)throws ServiceBizException;

    public PageInfoDto findBookingOrder(String vin, String license)throws ServiceBizException;

    public PageInfoDto QuerySelectOwner(Map<String, String> queryParam)throws ServiceBizException;

    public PageInfoDto QueryselectEmployee(Map<String, String> queryParam)throws ServiceBizException;

    public PageInfoDto queryPartStocks(Map<String, String> queryParam)throws ServiceBizException;

    public PageInfoDto queryLimit(Map<String, String> queryParam)throws ServiceBizException;

    public PageInfoDto queryLimitorder(Map<String, String> queryParam)throws ServiceBizException;

    public List<Map> getBookingTypes()throws ServiceBizException;

    public PageInfoDto querybookingpart(String bookingOrderNo)throws ServiceBizException;

    public PageInfoDto querybookingpartitem(String bookingOrderNo)throws ServiceBizException;

    public PageInfoDto querybookingitem(String bookingOrderNo)throws ServiceBizException;

}

