package com.yonyou.dms.vehicle.service.dlrinvtyManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface DlrVehicleQueryService {

	/**
	 * 查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto query(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 详细查询
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	public Map<String, Object> detailQuery(Long id)throws ServiceBizException;

	/**
	 * 变更日志查询
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto oemVehicleDetailed(Map<String, String> queryParam)throws ServiceBizException;

	/**
	 * 下载
	 * @param queryParam
	 * @param response
	 * @param request
	 * @throws ServiceBizException
	 */
	public void findQuerySuccList(Map<String, String> queryParam, HttpServletResponse response,
			HttpServletRequest request)throws ServiceBizException;


}
