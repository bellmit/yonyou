package com.yonyou.dms.vehicle.service.activityRecallManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.ActivityManageDTO;

/**
* @author liujm
* @date 2017年4月24日
*/
@SuppressWarnings("all")

public interface RecallVehicleSeraceService {
	
	//返厂未召回车辆查询
	public PageInfoDto recallVehicleSeraceQuery(Map<String,String> queryParam) throws ServiceBizException;
	//返厂未召回车辆下载
	public void recallVehicleSeraceDownload(Map<String, String> queryParam, HttpServletRequest request,
						HttpServletResponse response) throws ServiceBizException;
	
	//查询召回活动信息
	public Map queryRecallDateil(String vin,String recallId) throws ServiceBizException;
	//查询客户信息
	public Map queryCustomerDateil(String vin,String recallId) throws ServiceBizException;
	//查询车辆信息
	public Map queryVehicleDateil(String vin,String recallId) throws ServiceBizException;
	//修改预计召回时间
	public void updateRecallTime(ActivityManageDTO amDto) throws ServiceBizException;
}















