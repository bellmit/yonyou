package com.yonyou.dms.vehicle.service.k4OrderExecuteManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * 
* @ClassName: ConfirmOrderModifyService 
* @Description: JV订单执行管理
* @author zhengzengliang 
* @date 2017年3月8日 下午5:49:15 
*
 */
@SuppressWarnings("rawtypes")
public interface ConfirmOrderModifyService {

	public PageInfoDto getConfirmOrderInfoQueryList(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> selectOrderId(String orderNo) throws ServiceBizException;

	public List<Map> selectTiK4VsOrder(String orderNo) throws ServiceBizException;

	public List<Map> queryWeek(Map<String, String> queryParam) throws ServiceBizException;

}
