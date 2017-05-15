package com.yonyou.dms.report.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 车辆库存明细
 * 
 * @author
 *
 */
public interface SaleReportCarStoresService {
	/**
	 * 导出车辆库存明细
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> exportSaleReportCarStores(Map<String, String> queryParam) throws ServiceBizException, SQLException;

	/**
	 * 分页查询
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto querySaleReportCarStoresInfo(Map<String, String> queryParam)
			throws ServiceBizException, SQLException;

	/**
	 * 查询仓库
	 * 
	 * @param queryParam
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryStorage(Map<String, String> queryParam) throws ServiceBizException;

	/**
	 * 查询部门
	 * 
	 * @param queryParam
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryOrg(Map<String, String> queryParam) throws ServiceBizException;
}
