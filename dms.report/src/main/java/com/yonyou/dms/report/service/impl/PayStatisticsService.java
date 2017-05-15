package com.yonyou.dms.report.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface PayStatisticsService {
	/**
	 * 导出收款统计报表
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> exportPayStatistics(Map<String, String> queryParam) throws ServiceBizException, SQLException;

	/**
	 * 分页查询
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryPayStatisticsInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException;

	/**
	 * 查询付款方式
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public List<Map> querypayType(Map<String, String> queryParam) throws ServiceBizException, SQLException;

}
