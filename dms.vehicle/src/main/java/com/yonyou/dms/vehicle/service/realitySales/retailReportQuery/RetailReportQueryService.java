package com.yonyou.dms.vehicle.service.realitySales.retailReportQuery;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface RetailReportQueryService {

	/**
	 * 零售上报查询查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto retailReportQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 零售上报查询下载
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public List<Map> retailReportQuerySuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 根据ID获取零售上报查询信息详情
	 * @param id
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public Map<String, Object> queryDetail(Long nvdrId, LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 零售上报查询查询(经销商端)
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto retailDealerReportQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 根据ID获取零售上报查询信息详情(经销商端)
	 * @param id
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public Map<String, Object> queryDealerDetail(String id, LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 零售上报查询下载(经销商)
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public List<Map> retailDealerReportQuerySuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

}
