package com.yonyou.dms.customer.service.protect;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.customer.RepairExpireRemindDTO;
import com.yonyou.dms.common.domains.DTO.customer.TermlyMaintainRemindDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface protectService {
	/**
	 * 分页查询
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryProtectInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException;
	
	/**
	 * 导出保修
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> exportProtectInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException;
	
	/**
	 * 新增提醒记录
	 * 
	 * @param id
	 * @param ttcampaignPlanDTO
	 */
	public void addeRemind(String vins, String ownerNo, RepairExpireRemindDTO repairExpireRemindDTO) throws ServiceBizException;
	
	/**
	 * 根据VIN,车主编号进行分页查询提醒记录信息
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto queryRemindre(String vin, String ownerNo) throws ServiceBizException;
	
	/**
	 * 根据提醒编号进行回显查询提醒记录信息
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryByRemindID(String remindId) throws ServiceBizException;
	
	/**
	 * 根据提醒编号進行修改提醒记录
	 * 
	 * @param 
	 * @return
	 * @throws ServiceBizException
	 */
	public void modifyRemindID(String remindId,RepairExpireRemindDTO repairExpireRemindDTO) throws ServiceBizException;
	
	/**
	 * 查询DCRC回访信息
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	List<Map> querySales(Map<String, String> queryParam) throws ServiceBizException;
}
