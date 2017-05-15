package com.yonyou.dms.report.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 导出展厅来源分析报表
 * 
 * @author
 *
 */
public interface PotentialCustomersReportService {
	/**
	 * 导出展厅来源分析报表
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> exportPotentialCustomersStreamAnalysis(Map<String, String> queryParam)
			throws ServiceBizException, SQLException;

	/**
	 * 查询展厅来源
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto queryVisitingRecordInfo(Map<String, String> queryParam) throws ServiceBizException;
}
