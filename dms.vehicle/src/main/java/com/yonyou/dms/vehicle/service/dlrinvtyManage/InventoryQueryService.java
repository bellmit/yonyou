package com.yonyou.dms.vehicle.service.dlrinvtyManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface InventoryQueryService {

	/**
	 * 经销商库存汇总查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto oemInventoryTotalQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 经销商库存明细查询(oem)
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto oemInventoryQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 经销商库存汇总查询信息下载
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public List<Map> findTotalQuerySuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 经销商库存明细查询信息下载
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public List<Map> findInventoryQuerySuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

}
