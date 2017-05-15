
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : queryHopeCusService.java
*
* @Author : zhanshiwei
*
* @Date : 2017年1月20日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月20日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.report.service.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 有望客户跟踪进度
* @author zhanshiwei
* @date 2017年1月20日
*/

public interface queryHopeCusService {
    public PageInfoDto queryHopeCustomer( Map<String, String> param) throws ServiceBizException, ParseException;
    @SuppressWarnings("rawtypes")
	public List<Map> printHopeCustomer(String startDate,String soldBy) throws ServiceBizException;
}
