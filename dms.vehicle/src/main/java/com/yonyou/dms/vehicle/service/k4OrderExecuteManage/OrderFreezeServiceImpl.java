package com.yonyou.dms.vehicle.service.k4OrderExecuteManage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.k4OrderExecuteManage.OrderFreezeDao;

/**
 * 
* @ClassName: OrderFreezeServiceImpl 
* @Description: JV订单执行管理
* @author zhengzengliang 
* @date 2017年3月13日 下午3:40:50 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class OrderFreezeServiceImpl implements OrderFreezeService{
	
	@Autowired
	private OrderFreezeDao orderFreezeDao;

	@Override
	public PageInfoDto orderFreezeQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = orderFreezeDao.orderFreezeQuery(queryParam);
		return pageInfoDto;
	}

	@Override
	public List<Map> getFreezeReason(String freezeReason) throws ServiceBizException {
		List<Map> list = orderFreezeDao.getFreezeReason(freezeReason);
		return list;
	}

	@Override
	public List<Map> findFreezeReason() throws ServiceBizException {
		List<Map> list = orderFreezeDao.findFreezeReason();
		return list;
	}

}
