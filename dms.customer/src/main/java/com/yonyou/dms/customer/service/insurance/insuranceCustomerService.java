package com.yonyou.dms.customer.service.insurance;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.customer.InsuranceExpireRemindDTO;
import com.yonyou.dms.common.domains.DTO.customer.OwnerDTO;
import com.yonyou.dms.common.domains.DTO.customer.TermlyMaintainRemindDTO;
import com.yonyou.dms.common.domains.DTO.customer.TmVehicleDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface insuranceCustomerService {
	/**
	 * 分页查询
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryInsuranceInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException;


	/**
	 * 查询险种定义
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryInsurersTypeName(Map<String, String> queryParam) throws ServiceBizException;
	
	/**
	 * 导出客户关怀
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> exportInsuranceInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException;
	
	/**
	 * 根据车主编号进行回显进行车主信息修改
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryOwnerNoByid(String id) throws ServiceBizException;
	
	/**
	 * 修改车主信息
	 * 
	 * @param id
	 * @param ttcampaignPlanDTO
	 */
	public void updateOwner(String ownerNo, OwnerDTO ownerDTO) throws ServiceBizException;
	
	/**
	 * 修改车辆信息
	 * 
	 * @param id
	 * @param ttcampaignPlanDTO
	 */
	public void updateVehicle(String vin, TmVehicleDTO tmVehicleDTO) throws ServiceBizException;
	
	/**
	 * 分页查询保险保修
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryInsuranceTypeNameInfo(Map<String, String> queryParam, String vin) throws ServiceBizException, SQLException;
	
	/**
	 * 根据车辆VIN,车牌号进行回显查找
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryVehicelByid(String vin, String ownerNo) throws ServiceBizException;
	
	/**
	 * 新增提醒记录
	 * 
	 * @param id
	 * @param ttcampaignPlanDTO
	 */
	public void addeRemind(String vins, String ownerNo, InsuranceExpireRemindDTO insuranceExpireRemindDTO) throws ServiceBizException;
	
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
	public void modifyRemindID(String id,InsuranceExpireRemindDTO insuranceExpireRemindDTO) throws ServiceBizException;
	
	/**
	 * 查询销售回访
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	List<Map> querySales(Map<String, String> queryParam) throws ServiceBizException;


}
