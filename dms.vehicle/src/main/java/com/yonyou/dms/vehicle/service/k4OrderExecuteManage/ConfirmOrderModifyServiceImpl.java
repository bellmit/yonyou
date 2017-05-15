package com.yonyou.dms.vehicle.service.k4OrderExecuteManage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.k4OrderExecuteManage.ConfirmOrderModifyDao;
/**
 * 
* @ClassName: ConfirmOrderModifyServiceImpl 
* @Description: JV订单执行管理
* @author zhengzengliang 
* @date 2017年3月8日 下午5:17:11 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class ConfirmOrderModifyServiceImpl implements ConfirmOrderModifyService{

	@Autowired
	private ConfirmOrderModifyDao  confirmOrderModifyDao ;
	
	@Override
	public PageInfoDto getConfirmOrderInfoQueryList(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = confirmOrderModifyDao.getConfirmOrderInfoQueryList(queryParam);
		return pageInfoDto;
	}

	@Override
	public List<Map> selectOrderId(String orderNo) throws ServiceBizException {
		List<Map> list = confirmOrderModifyDao.selectOrderId(orderNo);
		return list;
	}

	@Override
	public List<Map> selectTiK4VsOrder(String orderNo)
			throws ServiceBizException {
		List<Map> list = confirmOrderModifyDao.selectTiK4VsOrder(orderNo);
		return list;
	}

	@Override
	public List<Map> queryWeek(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> list = confirmOrderModifyDao.queryWeek(queryParam);
		return list;
	}

}
