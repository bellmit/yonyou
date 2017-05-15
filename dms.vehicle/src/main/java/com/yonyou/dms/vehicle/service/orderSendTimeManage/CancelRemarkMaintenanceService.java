package com.yonyou.dms.vehicle.service.orderSendTimeManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.k4OrderQueryManage.TmOrderCancelRemarksDTO;

/**
 * 取消备注维护
 * @author Administrator
 *
 */

public interface CancelRemarkMaintenanceService {
	
	//取消备注维护查询
	public PageInfoDto  CancelRemarkQuery(Map<String, String> queryParam);
	
	//新增取消备注维护
	  public Long addCancelRemark(TmOrderCancelRemarksDTO ptdto) throws ServiceBizException ;
	  
	  //修改时获得取消备注维护代码
	  public String getId();

	public Map findDetailById(Long id);

	public Long editCancelRemark(TmOrderCancelRemarksDTO ptdto);

}
