package com.yonyou.dms.customer.service.monitor;

import java.sql.SQLException;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.TtcampaignPlanDTO;
import com.yonyou.dms.common.domains.DTO.customer.MontiorVehicleDTO;
import com.yonyou.dms.common.domains.DTO.customer.SuggestComplaintDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface monitorService {
	/**
	 * 分页查询监控车主车辆信息
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryMonitorInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException;

	/**
	 * 新增监控车主车辆信息
	 * 
	 * @param id
	 * @param ttcampaignPlanDTO
	 */
	public void addComplaint(MontiorVehicleDTO montiorVehicleDTO) throws ServiceBizException;
	
	/**
	 * 修改监控车主车辆信息
	 * 
	 * @param id
	 * @param ttcampaignPlanDTO
	 */
	public void updateComplaint(String monitorId, MontiorVehicleDTO montiorVehicleDTO);
	
	/**
	 * 分页查询监控车主车辆历史信息
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryMonitorHistory(Map<String, String> queryParam) throws ServiceBizException, SQLException;
	
	/**
	 * 删除车主车辆监控信息
	 * 
	 * @param id
	 * @throws ServiceBizException
	 */
	public void deletePlanById(String monitorId) throws ServiceBizException;
}
