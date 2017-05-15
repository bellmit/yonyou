package com.yonyou.dms.vehicle.service.k4OrderExecuteManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: OrderCancelService 
* @Description: JV订单执行管理
* @author zhengzengliang 
* @date 2017年3月10日 下午4:03:38 
*
 */
@SuppressWarnings("rawtypes")
public interface OrderCancelService {

	public PageInfoDto findAll(Map<String, String> queryParam) throws ServiceBizException ;

	public List<Map> orderCancelService() throws ServiceBizException;

}
