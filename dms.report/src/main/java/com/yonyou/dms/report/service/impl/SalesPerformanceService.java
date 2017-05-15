
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : SalesPerformanceService.java
*
* @Author : zhanshiwei
*
* @Date : 2017年2月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月17日    zhanshiwei    1.0
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
 * 销售业绩一览报表
 * 
 * @author zhanshiwei
 * @date 2017年2月17日
 */

public interface SalesPerformanceService {

    public PageInfoDto querySalesPerformance(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto querySalesPerformanceDetail(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> SalesPerformanceExprot(Map<String, String> queryParam) throws ServiceBizException;//按销售顾问导出
	
	public List<Map> SeriesExprot(Map<String, String> queryParam) throws ServiceBizException;//按车系导出
}
