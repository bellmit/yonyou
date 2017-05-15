package com.yonyou.dms.vehicle.service.activityManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* @author liujiming
* @date 2017年3月31日
*/
public interface ActivityIssueMaintainService {

	//服务活动发布 查询
	public PageInfoDto  getActivityIssueQuery(Map<String, String> queryParam) throws ServiceBizException;
	
	//服务活动发布 查询
	public PageInfoDto  getActivityIssueQueryDealer(Map<String, String> queryParam,Long activityId) throws ServiceBizException;
	//发布	
	public void   activityIssue(Long activityId) throws ServiceBizException; 
	//发布	
	public void   activityCancleIssue(Long activityId) throws ServiceBizException; 
}
