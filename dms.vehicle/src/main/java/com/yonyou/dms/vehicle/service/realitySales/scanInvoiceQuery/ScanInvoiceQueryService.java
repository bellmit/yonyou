package com.yonyou.dms.vehicle.service.realitySales.scanInvoiceQuery;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface ScanInvoiceQueryService {

	/**
	 * 汇总查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto oemScanInvoiceTotalQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 明细查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto oemScanInvoiceQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;
	
	/**
	 * 扫描发票汇总查询信息下载
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public List<Map> findTotalQuerySuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 扫描发票明细查询信息下载
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public List<Map> findInventoryQuerySuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;


}
