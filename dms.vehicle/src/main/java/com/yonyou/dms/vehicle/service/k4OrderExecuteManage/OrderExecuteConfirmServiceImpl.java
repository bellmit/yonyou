package com.yonyou.dms.vehicle.service.k4OrderExecuteManage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.k4OrderExecuteManage.OrderExecuteConfirmDao;

/**
 * 
 * @ClassName: OrderExecuteConfirmServiceImpl
 * @Description: JV订单执行管理
 * @author zhengzengliang
 * @date 2017年3月3日 下午5:28:37
 *
 */
@SuppressWarnings("rawtypes")
@Service
public class OrderExecuteConfirmServiceImpl implements OrderExecuteConfirmService {

	@Autowired
	private OrderExecuteConfirmDao orderExecuteConfirmDao;

	@Override
	public List<Map> queryWeek() throws ServiceBizException {
		List<Map> list = orderExecuteConfirmDao.queryWeek();
		return list;
	}

	@Override
	public PageInfoDto getOrderExecuteConfirmInfoQueryList(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = orderExecuteConfirmDao.getOrderExecuteConfirmInfoQueryList(queryParam);
		return pageInfoDto;
	}

	@Override
	public List<Map> getAddressAndDealerCode() throws ServiceBizException {
		List<Map> list = orderExecuteConfirmDao.getAddressAndDealerCode();
		return list;
	}

	@Override
	public List<Map> getPaymentList() throws ServiceBizException {
		List<Map> list = orderExecuteConfirmDao.getPaymentList();
		return list;
	}

	@Override
	public Map<String, Object> orderExecuteConfirmValidate(Map<String, String> queryParam) throws ServiceBizException {
		String[] arrOrderNos = queryParam.get("orderNos").split(",");

		int flag = 0;
		if (arrOrderNos.length > 0) {
			for (int i = 0; i < arrOrderNos.length; i++) {
				String orderNo = arrOrderNos[i];
				List<Map> tvoList = orderExecuteConfirmDao.selectVehicleUse(orderNo);
				if (tvoList.size() > 0) {
					for (Map po : tvoList) {
						if (!OemDictCodeConstants.VEHICLE_USAGE_TYPE_68.equals(po.get("VEHICLE_USE"))
								&& OemDictCodeConstants.IF_TYPE_YES.toString().equals(queryParam.get("isRebate"))) {
							// flag = 2;
							break;
						}
					}
				}

			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FLAG", flag);
		return map;
	}

	@Override
	public Map<String, Object> checkOrderNoStatus(Map<String, String> queryParam) throws ServiceBizException {
		String[] arrOrderNos = queryParam.get("orderNos").split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		// 校验所有订单是否在可确认状态
		for (int i = 0; i < arrOrderNos.length; i++) {
			Integer flag2 = orderExecuteConfirmDao.checkOrderNoStatus(arrOrderNos[i].toString(), 1);
			if (flag2 != 1) {
				map.put("FLAG", false);
				return map;
			}
		}
		map.put("FLAG", true);
		return map;
	}

	@Override
	public List<Map> selectTtVsOrder(String orderNo) throws ServiceBizException {
		List<Map> tvoList = orderExecuteConfirmDao.selectVehicleUse(orderNo);
		return tvoList;
	}

	@Override
	public List<TmDealerPO> selectTmDealerByDealerCode(String dealerCode) throws ServiceBizException {
		List<TmDealerPO> list = orderExecuteConfirmDao.selectTmDealerByDealerCode(dealerCode);
		return list;
	}

	@Override
	public List<TmDealerPO> selectTmDealerByDealerId(BigDecimal dealerId) throws ServiceBizException {
		List<TmDealerPO> list = orderExecuteConfirmDao.selectTmDealerByDealerId(dealerId);
		return list;
	}

	@Override
	public List<Map> selectVwMaterial(Long materialId) throws ServiceBizException {
		List<Map> list = orderExecuteConfirmDao.selectVwMaterial(materialId);
		return list;
	}

	@Override
	public String getEndDate(Integer orderYear, Integer orderWeek) throws ServiceBizException {
		String endDate = orderExecuteConfirmDao.getEndDate(orderYear, orderWeek);
		return endDate;
	}

	@Override
	public void insertHistory(Long orderId, Integer saleOrderType06, String orderExecute, String string2, Long userId,
			String userName) throws ServiceBizException {
		orderExecuteConfirmDao.insertHistory(orderId, saleOrderType06, orderExecute, string2, userId, userName);
	}

	@Override
	public void updateTtVsOrder(String paymentType, Integer isRebate, String orderNo) throws ServiceBizException {
		// TODO Auto-generated method stub
		orderExecuteConfirmDao.updateTtVsOrder(paymentType, isRebate, orderNo);
	}

}
