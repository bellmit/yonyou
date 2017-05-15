/**
 * 
 */
package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.customer.OwnerDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.WorkGroupDto;

/**
 * @author sqh
 *
 */
public interface QueryByLinsenceService {

	public PageInfoDto queryByLinsence(Map<String, String> queryParam) throws ServiceBizException;// 通过车牌号查询车辆车主信息
	
	public List<Map> queryByLinsence2(Map<String, String> queryParam) throws ServiceBizException;// 通过车牌号查询车辆车主信息
	
	public PageInfoDto queryTripleInfoByVin(Map<String, String> queryParam) throws ServiceBizException;// 查询车辆预警
	
	public PageInfoDto QueryOwnerByNoOrSpell(Map<String, String> queryParam) throws ServiceBizException;// 查询车主信息
	
	/**
	 * 根据vin进行回显
	 * 
	 * @param vin
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> queryOwnerNOByVin(String vin) throws ServiceBizException;
	
	public PageInfoDto querywechatcardmessageRO(Map<String, String> queryParam) throws ServiceBizException;// 查询优惠券
	
	public void addOwner(OwnerDTO ownerDTO) throws ServiceBizException;
	
	public void deleteRepairOrder(Map<String, String> queryParam) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<Map> querySalesPartByRoNO(String id) throws ServiceBizException;// 查询销售材料

	public int CheckActivityOem(Map<String, String> queryParam) throws ServiceBizException;// 查询下发活动数量
	
	@SuppressWarnings("rawtypes")
	public List<Map> conferDiscountMode(Map<String, String> queryParam);// 优惠折扣授权
	
	@SuppressWarnings("rawtypes")
	public List<Map> queryIsRestrict(Map<String, String> queryParam);// 查询是否行管经销商和本牌车
	
	@SuppressWarnings("rawtypes")
	public List<Map> queryOEMTAG(String vin) throws ServiceBizException;//根据条件查询车辆库存信息

	public String queryLabourCode(Map<String, String> queryParam) throws ServiceBizException;//查询维修项目是否符合保存要求
}
