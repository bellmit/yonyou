
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : RepairOrderReportService.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月27日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.report.service.impl;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 维修业务查询
 * 
 * @author zhanshiwei
 * @date 2016年9月27日
 */

public interface RepairOrderReportService {

    public PageInfoDto queryRepairOrders(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto queryEnterFactory(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto queryEemployeeemployeeRepair(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto labourAmount(Map<String, String> queryParam) throws ServiceBizException;
    public List<Map>  quRepairType(Map<String, String> queryParam) throws ServiceBizException;
    public List<Map> exportenterFactory(Map<String, String> queryParam) throws ServiceBizException;

}
