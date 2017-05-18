package com.yonyou.dms.customer.service.vehicleLoss;


import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.customer.LossVehicleRemindDTO;
import com.yonyou.dms.common.domains.DTO.customer.OwnerDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface VehicleLossRemindService {
	/**
	 * 查询客户流失报警s
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto queryVehicleLossRemind(Map<String, String> queryParam) throws ServiceBizException;
	
	/**
	 * 查询客户流失报警s
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryVehicleLossRemindByVin(Map<String, String> queryParam) throws ServiceBizException;
	/**
	 * 根据车主编号进行回显查找
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> queryOwnerNOByid(String id) throws ServiceBizException;
	/**
	 * 根据车主编号进行车主信息修改
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	public void modifyOwnerNOByid(String id,OwnerDTO ownerDto) throws ServiceBizException;
	
	/**
	 * 根据车辆VIN,车牌号进行回显查找
	 * 
	 * @param vin
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryVehicelByVin(String vin) throws ServiceBizException;
	
	/**
	 * 新增提醒记录
	 * 
	 * @param id
	 * @param ttcampaignPlanDTO
	 */
	public void addRemind(LossVehicleRemindDTO lossVehicleRemindDTO) throws ServiceBizException;
	
	/**
	 * 根据VIN,车主编号进行回显查询提醒记录信息
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto queryRemindre(String vin,String ownerNo) throws ServiceBizException;
	
	/**
	 * 根据提醒編號进行回显查询提醒记录信息
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryRemindID(String remindId) throws ServiceBizException;
	
	/**
	 * 根据提醒編號進行修改提醒记录
	 * 
	 * @param 
	 * @return
	 * @throws ServiceBizException
	 */
	public void modifyRemindID(String remindId,LossVehicleRemindDTO lossVehicleRemindDTO) throws ServiceBizException;
	
	/**
	 * 查询DCRC专员
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	List<Map> querySales(Map<String, String> queryParam) throws ServiceBizException;
	
	/**
	 * 查询维修建议项目
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	PageInfoDto QueryRepairSuggest(Map<String, String> queryParam) throws ServiceBizException;
	
	/**
	 * 查询维修建议配件
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	PageInfoDto QueryRepair(Map<String, String> queryParam) throws ServiceBizException;
	
	/**
	 * 查询投诉历史
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	PageInfoDto QueryComplaint(Map<String, String> queryParam) throws ServiceBizException;
	
	/**
	 * 导出客户流失报警
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> exportVehicleLossRemind(Map<String, String> queryParam) throws ServiceBizException;
}
