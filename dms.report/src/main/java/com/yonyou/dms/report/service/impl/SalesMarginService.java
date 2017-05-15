
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : SalesMarginService.java
*
* @Author : zhanshiwei
*
* @Date : 2017年2月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月7日    zhanshiwei    1.0
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
* 销售毛利
* @author zhanshiwei
* @date 2017年2月7日
*/

public interface SalesMarginService {
    public PageInfoDto querySalesMargin( Map<String, String> queryParam) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
   	public List<Map> querySalesMarginExport(Map<String, String> queryParam) throws ServiceBizException;//导出excel
}
