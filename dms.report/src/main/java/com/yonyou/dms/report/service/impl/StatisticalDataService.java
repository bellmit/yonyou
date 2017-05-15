
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : StatisticalDataService.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月23日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月23日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.report.service.impl;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 首页统计
 * 
 * @author zhanshiwei
 * @date 2016年9月23日
 */

public interface StatisticalDataService {

    public Map<String, Object> queryComplainCounts() throws ServiceBizException;

    public Map<String, Object> queryCurrentMonthSales() throws ServiceBizException;

    public Map<String, Object> StatisticalData() throws ServiceBizException;

    public Map<String, Object> queryCurrentMonthRepairs() throws ServiceBizException;

    public List<List<Object>> queryRepairs() throws ServiceBizException;

    public List<Map> queryRepairCon() throws ServiceBizException;
}
