package com.yonyou.dms.vehicle.service.orderSendTimeManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.k4OrderQueryManage.TmOrderfreezereasonDTO;

/**
 * 接通原因维护
 * @author Administrator
 *
 */
public interface OrderFreezeReasonService {
	//截停原因查询
	public PageInfoDto FreezeReasonQuery(Map<String, String> queryParam) ;
	//通过id查询进行回显信息
	
	public String getAddOrderReasonId();
	
	public void add(TmOrderfreezereasonDTO dto);
	public Map<String, Object> findById(Long freezeId);
	public void edit(TmOrderfreezereasonDTO dto);

}
