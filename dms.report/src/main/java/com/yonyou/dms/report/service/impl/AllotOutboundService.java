
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : AllotOutboundService.java
*
* @Author : DuPengXin
*
* @Date : 2016年10月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月17日    DuPengXin   1.0
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
* 调拨出库统计接口
* @author DuPengXin
* @date 2016年10月17日
*/

public interface AllotOutboundService {
    public PageInfoDto queryAllotOutbound(Map<String, String> queryParam) throws ServiceBizException;
    public List<Map> queryAllotOutboundExport(Map<String,String> queryParam) throws ServiceBizException;
}
