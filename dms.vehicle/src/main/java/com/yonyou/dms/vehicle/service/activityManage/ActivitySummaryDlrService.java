package com.yonyou.dms.vehicle.service.activityManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.ActivitySummaryDTO;

/**
* @author liujiming
* @date 2017年4月6日
*/
public interface ActivitySummaryDlrService {
	//查询
	public PageInfoDto activitySummaryDlrQuery(Map<String, String> queryParam) throws ServiceBizException;
	//维护查询
	public Map activitySummaryDlrDetailQuery(String activityCode ,String activityName, Integer inAmount,Long activityId) throws ServiceBizException;
	//上报
	public void activitySummaryUpdateSave(ActivitySummaryDTO asDto) throws ServiceBizException;
}
