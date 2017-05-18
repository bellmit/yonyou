package com.yonyou.dms.customer.service.complaint;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface searchComplaintService {
	/**
	 * 分页查询客户投诉查询
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryCompaint(Map<String, String> queryParam) throws ServiceBizException, SQLException;
	
	/**
	 * 根据车主编号、工单号进行回显查找
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> querycomplaintNoById(String id) throws ServiceBizException;
	
	/**
	 * 根据发动机号、车牌号、车主地址查询建议维修项目
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryDispose(String complaintNo,Map<String, String> queryParam) throws ServiceBizException, SQLException;
	
	/**
	 * 导出客户投诉
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> exportComalaint(Map<String, String> queryParam) throws ServiceBizException, SQLException;
	
	/**
	 * 删除客户投诉
	 * 
	 * @param id
	 * @throws ServiceBizException
	 */
	public void deletePlanById(String complaintNo) throws ServiceBizException;
}
