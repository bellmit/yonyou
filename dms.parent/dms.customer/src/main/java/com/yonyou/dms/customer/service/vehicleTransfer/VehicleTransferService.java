/**
 * 
 */
package com.yonyou.dms.customer.service.vehicleTransfer;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @author sqh
 *
 */
public interface VehicleTransferService {
	/**
	 * 根据车主编号进行回显
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryOwnerNoByid(String id) throws ServiceBizException;
	
	public List<Map> ModifyOwnerById(Map<String, String> queryParam) ;
	
	public List<Map> queryByLinsence(Map<String, String> queryParam) throws ServiceBizException;// 通过车牌号查询车辆车主信息
}
