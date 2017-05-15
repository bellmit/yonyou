package com.yonyou.dms.vehicle.service.k4OrderExecuteManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: OrderTopMaintainService 
* @Description: JV订单执行管理
* @author zhengzengliang 
* @date 2017年3月9日 下午2:48:00 
*
 */
@SuppressWarnings("rawtypes")
public interface OrderTopMaintainService {

	
	public List<Map> queryYear() throws ServiceBizException;

	public PageInfoDto findAll(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> selectTtVsOrderAll(String orderNo) throws ServiceBizException;

	public List<Map> getFreezeReason(String cancelReason) throws ServiceBizException;

}
