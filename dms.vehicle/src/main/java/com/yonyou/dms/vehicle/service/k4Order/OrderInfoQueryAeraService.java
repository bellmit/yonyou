package com.yonyou.dms.vehicle.service.k4Order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
* @author liujiming
* @date 2017年3月2日
*/
public interface OrderInfoQueryAeraService {
	
	/**整车订单查询 明细下载*/
	public void findOrderDetailQueryDownload( Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;           
}
