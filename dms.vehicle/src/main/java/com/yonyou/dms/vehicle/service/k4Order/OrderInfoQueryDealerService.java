package com.yonyou.dms.vehicle.service.k4Order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
* @author liujiming
* @date 2017年3月8日
*/
public interface OrderInfoQueryDealerService {

	//整车订单查询 下载
	public void findOrderInfoQueryDealerDownload( Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;           
	
	//整车撤单(经销商)    详细车籍查询
	public Map<String, Object> findOrderVehicleByVIN(String vin) throws ServiceBizException;
	
}
