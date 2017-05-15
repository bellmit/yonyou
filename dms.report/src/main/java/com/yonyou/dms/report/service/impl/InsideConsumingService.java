
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : InsideConsumingService.java
*
* @Author : DuPengXin
*
* @Date : 2016年10月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月12日    DuPengXin   1.0
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
* 内部领用统计接口
* @author DuPengXin
* @date 2016年10月12日
*/

public interface InsideConsumingService {
    public PageInfoDto queryInsideConsuming(Map<String, String> queryParam) throws ServiceBizException;
    public List<Map> getInsideConsumingItems(Long id) throws ServiceBizException;
    public List<Map> queryInsideConsumingExport(Map<String, String> queryParam) throws ServiceBizException;
    public List<Map> queryInsideConsumingExportItems(Long id) throws ServiceBizException;

}
