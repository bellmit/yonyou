package com.yonyou.dms.vehicle.service.k4Order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.K4OrderDTO;

/**
* @author liujiming
* @date 2017年3月7日
*/
public interface FurtherOrderQueryService {
	//期货订单撤单
	public void modifyFurtherOrderCancle(K4OrderDTO k4OrderDTO)	throws ServiceBizException;
	
	//期货订单撤单下载
	public void furtherOrderDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response)	throws ServiceBizException;
}
