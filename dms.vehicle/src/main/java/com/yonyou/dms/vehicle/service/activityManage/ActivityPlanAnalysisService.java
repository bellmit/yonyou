package com.yonyou.dms.vehicle.service.activityManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* @author liujiming
* @date 2017年4月1日
*/
public interface ActivityPlanAnalysisService {
	//查询
	public PageInfoDto getPlanAnalysisInitQuery(Map<String, String> queryParam) throws ServiceBizException;
	//明细
	public PageInfoDto getPlanAnalysisDetailQuery(Map<String, String> queryParam,Long activityId) throws ServiceBizException;
	
	//责任明细
	public PageInfoDto getPlanAnalysisDetailQueryTwo(Map<String, String> queryParam,Long activityId) throws ServiceBizException;
		
}
