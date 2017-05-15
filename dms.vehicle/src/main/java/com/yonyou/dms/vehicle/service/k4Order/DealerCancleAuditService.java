package com.yonyou.dms.vehicle.service.k4Order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.function.exception.ServiceBizException;



/**
*
* 
* @author liujiming
* @date 2017年2月16日
*/

public interface DealerCancleAuditService {
	
	//经销商撤单查询下载（小区）
	public void findCancleAuditDownload( Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
}
