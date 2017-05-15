package com.yonyou.dms.vehicle.service.k4Order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.K4OrderDTO;

/**
 *
 * 
 * @author liujiming
 * @date 2017年2月28日
 */
public interface OrderRepealQueryService {

	// 整车撤单查询下载
	public void findOrderRepealQueryDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException;

	// 整车撤单明细查询
	public Map<String, Object> findOrderRepealById(long id) throws ServiceBizException;

	// 撤单
	public void queryPass(K4OrderDTO k4OrderDTO);

}
