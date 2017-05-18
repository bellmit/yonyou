package com.yonyou.dms.customer.service.complaint;

import java.sql.SQLException;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.customer.SuggestComplaintDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface customerComplaintService {
	/**
	 * 新增客户投诉
	 * @param id
	 * @param ttcampaignPlanDTO
	 */
	public void addComplaint(SuggestComplaintDTO suggestComplaintDTO) throws ServiceBizException;
	
	/**
	 * 分页查询部门
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryOrganization(Map<String, String> queryParam) throws ServiceBizException, SQLException;
	
	/**
	 * 查询单号
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto searchRepairOrder(Map<String, String> queryParam) throws ServiceBizException;// 查询
}
