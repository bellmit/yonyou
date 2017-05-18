package com.yonyou.dms.customer.service.suggestQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface suggestRepairService {
	/**
	 * 分页查询
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto querySuggestRepair(Map<String, String> queryParam) throws ServiceBizException, SQLException;

	/**
	 * 导出维修建议查询
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> exportSuggestRepair(Map<String, String> queryParam) throws ServiceBizException, SQLException;
}
