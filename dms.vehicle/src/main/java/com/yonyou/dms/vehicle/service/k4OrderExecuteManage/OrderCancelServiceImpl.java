package com.yonyou.dms.vehicle.service.k4OrderExecuteManage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.k4OrderExecuteManage.OrderCancelDao;
/**
 * 
* @ClassName: OrderCancelServiceImpl 
* @Description: JV订单执行管理
* @author zhengzengliang 
* @date 2017年3月10日 下午4:05:07 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class OrderCancelServiceImpl implements OrderCancelService{

	@Autowired
	private OrderCancelDao orderCancelDao;
	
	@Override
	public PageInfoDto findAll(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = orderCancelDao.findAll(queryParam) ;
		return pageInfoDto;
	}

	@Override
	public List<Map> orderCancelService() throws ServiceBizException {
		List<Map> list = orderCancelDao.orderCancelService();
		return list;
	}

}
