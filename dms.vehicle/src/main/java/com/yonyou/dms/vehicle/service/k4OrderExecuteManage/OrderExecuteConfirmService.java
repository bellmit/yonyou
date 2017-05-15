package com.yonyou.dms.vehicle.service.k4OrderExecuteManage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * 
* @ClassName: OrderExecuteConfirmService 
* @Description: JV订单执行管理
* @author zhengzengliang 
* @date 2017年3月3日 下午5:52:45 
*
 */
@SuppressWarnings("rawtypes")
public interface OrderExecuteConfirmService {
	
	public List<Map> queryWeek() throws ServiceBizException;

	public PageInfoDto getOrderExecuteConfirmInfoQueryList(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> getAddressAndDealerCode() throws ServiceBizException;

	public List<Map> getPaymentList() throws ServiceBizException;

	public Map<String, Object> orderExecuteConfirmValidate(Map<String, String> queryParam) throws ServiceBizException;

	public Map<String, Object> checkOrderNoStatus(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> selectTtVsOrder(String orderNo) throws ServiceBizException;

	public List<TmDealerPO> selectTmDealerByDealerCode(String dealerCode) throws ServiceBizException;

	public List<TmDealerPO> selectTmDealerByDealerId(BigDecimal dealerId) throws ServiceBizException;

	public List<Map> selectVwMaterial(Long materialId) throws ServiceBizException;

	public String getEndDate(Integer orderYear, Integer orderWeek) throws ServiceBizException;

	public void insertHistory(Long orderId, Integer saleOrderType06, String orderExecute, String string2, Long userId,
			String userName) throws ServiceBizException;

	public void updateTtVsOrder(String paymentType, Integer isRebate, String orderNo) throws ServiceBizException;

}
