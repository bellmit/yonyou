package com.yonyou.dms.vehicle.service.activityManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* @author liujiming
* @date 2017年3月31日
*/
public interface ActivityStatusMaintainService {
	//车辆活动状态查询
	public PageInfoDto activityStatusQuery(Map<String, String> queryParam) throws ServiceBizException;
	
	//车辆活动信息
	public Map getDetailCarMsgQuery(String VIN,Long activityId) throws ServiceBizException;
	
	//车辆活动信息
	public Map getDetailGTMMsgQuery(Long ctmId) throws ServiceBizException;
	
	//车辆活动信息
	public Map getDetailLinkManMsgQuery(String lmId) throws ServiceBizException;

	
}
