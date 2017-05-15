package com.yonyou.dms.repair.service.activity;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.activity.ActivityDTO;

public interface ActivityService {
	public PageInfoDto search(Map<String,String> param)throws ServiceBizException;

	public String[] addActivity(ActivityDTO activityDto)throws ServiceBizException;

	public PageInfoDto queryActivityVehicle(Map<String, String> param)throws ServiceBizException;

	public PageInfoDto queryVehicleDetail(String id)throws ServiceBizException;
}
