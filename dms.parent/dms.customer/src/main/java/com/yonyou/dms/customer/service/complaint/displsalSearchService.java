package com.yonyou.dms.customer.service.complaint;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface displsalSearchService {
	/**
	 * 分页查询客户投诉处理结果
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryCompaint(Map<String, String> queryParam) throws ServiceBizException, SQLException;
	
	/**
	 * 导出客户投诉处理结果
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> exportComalaint(Map<String, String> queryParam) throws ServiceBizException, SQLException;
}
