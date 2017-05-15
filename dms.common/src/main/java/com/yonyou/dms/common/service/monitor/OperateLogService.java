
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : OperateLogService.java
 *
 * @Author : yll
 *
 * @Date : 2016年7月14日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月14日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.common.service.monitor;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 操作日志的接口
 * @author yll
 * @date 2016年7月14日
 */

public interface OperateLogService {
	public PageInfoDto queryOperateLog(Map<String, String> queryParam) throws ServiceBizException;//根据参数查询
	public void writeOperateLog(OperateLogDto operateLogDto)throws ServiceBizException;
	public void recordOperateLog(String operateContent,Integer operateType,String... param);
    public List<Map> getAllSelect()throws ServiceBizException;//获取操作员下拉选
    public List<Map> queryToExport(Map<String, String> queryParam)throws ServiceBizException;//导出
	
	
}
