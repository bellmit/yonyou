package com.yonyou.dms.customer.service.solicitude;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.customer.OwnerDTO;
import com.yonyou.dms.common.domains.DTO.customer.TermlyMaintainRemindDTO;
import com.yonyou.dms.common.domains.DTO.customer.TmVehicleDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface solicitudeService {
	/**
	 * 分页查询
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto querySolicitudeInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException;

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
	 * 导出客户关怀
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> exportSolicitudeInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException;

	/**
	 * 新增提醒记录
	 * 
	 * @param id
	 * @param ttcampaignPlanDTO
	 */
	public void addeRemind(String vins, String ownerNo, TermlyMaintainRemindDTO termlyMaintainRemindDTO)
			throws ServiceBizException;

	/**
	 * 根据车辆VIN,车牌号进行回显查找
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryVehicelByid(String vin, String license) throws ServiceBizException;

	/**
	 * 根据车主编号进行回显查找
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryOwnerNOByid(String id) throws ServiceBizException;
	
	/**
	 * 根据车主编号、工单号进行回显查找
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryRoNoByid(String id,String roNo,String salesPartNo) throws ServiceBizException;

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
	public void modifyRemindID(String remindId,TermlyMaintainRemindDTO termlyMaintainRemindDTO) throws ServiceBizException;
	
	/**
	 * 查询DCRC专员
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryApplicant(Map<String, String> queryParam) throws ServiceBizException;

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
	 * 查询维修项目
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryMaintainHistory(Map<String, String> queryParam) throws ServiceBizException, SQLException;
	/**
	 * 查询维修历史
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryMaintainhistory(Map<String, String> queryParam) throws ServiceBizException, SQLException;

	
	/**
	 * 查询维修配件
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryPart(Map<String, String> queryParam) throws ServiceBizException, SQLException;


	/**
	 * 查询维修类型
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryRepairType(Map<String, String> queryParam) throws ServiceBizException;

	/**
	 * 根据VIN,车主编号进行回显查询提醒记录信息
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	/*@SuppressWarnings("rawtypes")
	List<Map> queryMaintain(String vin,String license, String ownername) throws ServiceBizException;*/

	/**
	 * 查询服务-保险续保办理结果
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	List<Map> queryInsurance(Map<String, String> queryParam, String vin) throws ServiceBizException;
	
	/**
	 * 查询工单明细维修项目明细
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryMainProject(Map<String, String> queryParam,String roNo) throws ServiceBizException, SQLException;
	
	/**
	 * 查询工单明细维修材料明细
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryMaintainMaterial(Map<String, String> queryParam,String roNo) throws ServiceBizException, SQLException;
	
	/**
	 * 查询工单明细销售材料明细
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto querySellMaterial(Map<String, String> queryParam,String roNo) throws ServiceBizException, SQLException;
	
	/**
	 * 查询工单附加项目明细
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryAccessoryProject(Map<String, String> queryParam,String roNo) throws ServiceBizException, SQLException;
	
	/**
	 * 查询工单辅料明细
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryAccessoryManage(Map<String, String> queryParam,String roNo) throws ServiceBizException, SQLException;
	
	/**
	 * 查询工单辅料明细
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryManage(Map<String, String> queryParam,String roNo) throws ServiceBizException, SQLException;
	
	/**
	 * 查询工单派工情况
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryAssign(Map<String, String> queryParam,String roNo) throws ServiceBizException, SQLException;
	
	/**
	 * 根据发动机号、车牌号、车主地址查询建议维修项目
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto QueryRepairProject(Map<String, String> queryParam) throws ServiceBizException, SQLException;
	
	/**
	 * 根据发动机号、车牌号、车主地址查询建议维修项目
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto QueryRepairPart(Map<String, String> queryParam) throws ServiceBizException, SQLException;
	
	/**
	 * 根据发动机号、车牌号、车主地址查询建议维修项目
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	public PageInfoDto queryComplaint(Map<String, String> queryParam) throws ServiceBizException, SQLException;
	
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
	public PageInfoDto queryDispose(Map<String, String> queryParam) throws ServiceBizException, SQLException;
}

