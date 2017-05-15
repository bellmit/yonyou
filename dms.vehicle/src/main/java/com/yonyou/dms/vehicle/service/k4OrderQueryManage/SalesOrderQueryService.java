package com.yonyou.dms.vehicle.service.k4OrderQueryManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SalesOrderQueryService {

	/**
	 * 加载查询
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam, LoginInfoDto loginInfo) throws ServiceBizException;

	/**
	 * 加载查询(经销商端)
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryDealerList(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException;

	/**
	 * 销售订单信息下载查询
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public List<Map> findSalesOrderSuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException;

	/**
	 * 根据ID 获取详细信息
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	public Map<String, Object> queryDetail(String id, LoginInfoDto loginInfo) throws ServiceBizException;

	/**
	 * 根据ID 获取详细信息(经销商端)
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	public Map<String, Object> queryDealerDetail(String id, LoginInfoDto loginInfo) throws ServiceBizException;

	/**
	 * 销售订单信息下载查询(经销商端)
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public List<Map> findDealerSalesOrderSuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException;

	/**
	 * 订单操作记录
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto orderRecords(Map<String, String> queryParam, LoginInfoDto loginInfo) throws ServiceBizException;

	public List<Map> findsalesOrder(String id);

}
