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
 * @date 2017年3月10日
 */

public interface CancelOrderApplyService {

	// 撤单申请(经销商)下载
	public void findCancelOrderApplyDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException;

	// 撤单申请(经销商) 申请修改表数据
	public void updateTmOrderPayChangeByVIN(K4OrderDTO k4OrderDTO) throws ServiceBizException;

}
