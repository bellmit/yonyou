package com.yonyou.dms.vehicle.service.k4Order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.function.exception.ServiceBizException;


/**
*
* 
* @author liujiming
* @date 2017年3月9日
*/

public interface CancelOrderApplyQueryService {
	

	//撤单查询(经销商)下载
	public void findCancelOrderApplyQueryDownload( Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
	

	
	
}
