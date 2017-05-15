package com.yonyou.dms.vehicle.service.k4OrderExecuteManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: OrderFreezeService 
* @Description: JV订单执行管理
* @author zhengzengliang 
* @date 2017年3月13日 下午3:39:55 
*
 */
@SuppressWarnings("rawtypes")
public interface OrderFreezeService {

	public PageInfoDto orderFreezeQuery(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> getFreezeReason(String freezeReason)throws ServiceBizException;

	public List<Map> findFreezeReason() throws ServiceBizException;

}
