package com.yonyou.dms.vehicle.service.paymentMaintenance;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.dealerPayment.TmDealerPaymentDTO;

/**
 * 经销商付款方式维护
 * @author Administrator
 *
 */
public interface PaymentMaintenanceService {
	//查询经销商付款方式
	public PageInfoDto  PaymentMaintenanceQuery(Map<String, String> queryParam) ;
     //修改付款方式信息
	public void modifyDealerPayment(Long id, TmDealerPaymentDTO ptdto);
	//通过id进行查询付款方式信息
	public Map getPaymentById(String id) ;
	//新增经销商付款方式
	 public Long addDealerPayment(TmDealerPaymentDTO ptdto) throws ServiceBizException ;
}
