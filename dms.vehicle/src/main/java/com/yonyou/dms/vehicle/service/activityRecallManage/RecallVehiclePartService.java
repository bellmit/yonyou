package com.yonyou.dms.vehicle.service.activityRecallManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* @author liujm
* @date 2017年4月21日
*/

public interface RecallVehiclePartService {
	
	//召回车辆配件满足率     查询
	public PageInfoDto recallVehiclePartQuery(Map<String,String> queryParam) throws ServiceBizException;
	//召回车辆配件满足率     下载
	public void recallVehiclePartDownload(Map<String, String> queryParam, HttpServletRequest request,
				HttpServletResponse response) throws ServiceBizException;
	
	//召回车辆配件满足率     明细下载
	public void recallVehiclePartDownloadDetail(Map<String, String> queryParam, 
			HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
	//召回车辆配件满足率     查询
	public PageInfoDto recallDetailVehiclePartQuery(String dealerCode, Long recallId, String groupNo) throws ServiceBizException;
	//召回车辆配件满足率     下载
	public void recallDetailVehiclePartDownload(String dealerCode, Long recallId, String groupNo, HttpServletRequest request,
				HttpServletResponse response) throws ServiceBizException;	
	
	
}



