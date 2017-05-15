package com.yonyou.dms.vehicle.service.orderSendTimeManage;


import com.yonyou.dms.common.domains.PO.basedata.TtOrderSendTimeManagePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.orderSendTimeManageDTO.TtOrderSendTimeManageDTO;

/**
 * 订单发送时间维护
 * @author Administrator
 *
 */
public interface OrderSendTimeManageService {
	//订单发送时间查询
	public PageInfoDto  orderSendTimeQuery();
	
	public void  deleteOrderSendTimeById(Long id);
	
	public TtOrderSendTimeManagePO getSendTimeById(Long id,TtOrderSendTimeManageDTO  dto);
	
	//修改订单发送时间
	public void modifySendTime(Long id,TtOrderSendTimeManageDTO ptdto) throws ServiceBizException;
	
	//添加订单发送时间
	public Long addOrdersendTime(TtOrderSendTimeManageDTO ptdto) throws ServiceBizException;

}
