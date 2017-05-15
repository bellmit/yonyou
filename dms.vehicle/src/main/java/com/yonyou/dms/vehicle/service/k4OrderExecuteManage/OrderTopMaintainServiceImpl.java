package com.yonyou.dms.vehicle.service.k4OrderExecuteManage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.k4OrderExecuteManage.OrderTopMaintainDao;
/**
 * 
* @ClassName: OrderTopMaintainServiceImpl 
* @Description: JV订单执行管理
* @author zhengzengliang 
* @date 2017年3月9日 下午2:48:54 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class OrderTopMaintainServiceImpl implements OrderTopMaintainService{

	@Autowired
	private OrderTopMaintainDao orderTopMaintainDao;
	
	@Override
	public List<Map> queryYear() throws ServiceBizException {
		List<Map> list = orderTopMaintainDao.queryYear();
		return list;
	}

	@Override
	public PageInfoDto findAll(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = orderTopMaintainDao.findAll(queryParam);
		return pageInfoDto;
	}

	@Override
	public List<Map> selectTtVsOrderAll(String orderNo) throws ServiceBizException {
		List<Map> list = orderTopMaintainDao.selectTtVsOrderAll(orderNo) ;
		return list;
	}

	@Override
	public List<Map> getFreezeReason(String cancelReason) throws ServiceBizException {
		List<Map> list = orderTopMaintainDao.getFreezeReason(cancelReason) ;
		return list;
	}
	
	
	

}
