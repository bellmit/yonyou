package com.yonyou.dms.vehicle.service.activityManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* @author liujiming
* @date 2017年4月5日
*/
public interface ActivityReportService {
	//查询
	public PageInfoDto activityReportQuery(Map<String, String> queryParam) throws ServiceBizException;
	
	//明细
	public PageInfoDto activityReportDetailQuery(Map<String, String> queryParam,String activityCode) throws ServiceBizException;
	
}
