/**
 * 
 */
package com.yonyou.dms.repair.service.claimRepairOrder;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.TtRoTimeoutDTO;
import com.yonyou.dms.common.domains.DTO.customer.LossVehicleRemindDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @author sqh
 *
 */
public interface ClaimRepairOrderService {

	/**
	 * 查询未结算索赔工单
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto queryClaimRepairOrder(Map<String, String> queryParam) throws ServiceBizException;
	
	@SuppressWarnings("rawtypes")
	public List<Map> queryClaimRepairOrders(Map<String, String> queryParam) throws ServiceBizException;
	
	/**
	 * 根据工单编号进行回显查找
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> QueryRoTimeoutCause(String id) throws ServiceBizException;
	
	public PageInfoDto QueryRoTimeoutDetail(String id) throws ServiceBizException;
	
	public List<Map> queryRoShortPartDetail(String id) throws ServiceBizException;
	
	public PageInfoDto queryRoTimeoutPartDetail(String id) throws ServiceBizException;
	
	public void MaintainRoTimeoutCauseAndDetail(Map timeoutDTO);
}
