package com.yonyou.dms.vehicle.service.activityRecallManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* @author liujm
* @date 2017年4月22日
*/

public interface ReturnToFactoryVehicleSeraceService {
	
	//返厂未召回车辆查询
	public PageInfoDto returnToFactoryVehicleSeraceQuery(Map<String,String> queryParam) throws ServiceBizException;
	//返厂未召回车辆下载
	public void returnToFactoryVehicleSeraceDownload(Map<String, String> queryParam, HttpServletRequest request,
					HttpServletResponse response) throws ServiceBizException;
}
