package com.yonyou.dms.vehicle.service.k4Order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.K4OrderDTO;

/**
 *
 * 
 * @author liujiming
 * @date 2017年2月16日
 */

public interface DealerOrderAuditService {

	// 经销商撤单审核
	public PageInfoDto queryInit(Map<String, String> queryParam) throws ServiceBizException;

	// 经销商撤单审核通过
	public void modifyOderPass(K4OrderDTO k4OrderDTO) throws ServiceBizException;

	// 经销商撤单驳回
	public void modifyOderReject(K4OrderDTO k4OrderDTO) throws ServiceBizException;

	// 经销商撤单查询
	public PageInfoDto queryAuditList(Map<String, String> queryParam) throws ServiceBizException;

	// 经销商撤单查询下载（小区）
	public void findCancleOrderDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException;

	// 经销商撤单审核下载（小区）
	public void findCancleOrderAuditDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException;

	public PageInfoDto queryInit1(Map<String, String> queryParam);

	public void modifyOderRejectSam(K4OrderDTO k4OrderDTO);

	public void modifyOderPass11(K4OrderDTO k4OrderDTO);

}
