
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : SaleReportService.java
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
 * 销售
 * 
 * @author zhanshiwei
 * @date 2016年9月27日
 */

public interface SaleReportService {

    public PageInfoDto queryVehicleSales(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto querySaleStatistics(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto queryconsultantDeeds(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto queryVehicleStock(Map<String, String> queryParam) throws ServiceBizException;

    public List<Map> exportVehicleSales(Map<String, String> queryParam) throws ServiceBizException;

    public List<Map> exportVehicleStock(Map<String, String> queryParam) throws ServiceBizException;

    public List<Map> exportSaleStatistics(Map<String, String> queryParam) throws ServiceBizException;

    public List<Map> exporconsultantDeeds(Map<String, String> queryParam) throws ServiceBizException;

}
