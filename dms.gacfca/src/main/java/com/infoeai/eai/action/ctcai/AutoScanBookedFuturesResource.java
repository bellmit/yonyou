package com.infoeai.eai.action.ctcai;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.ctcai.AutoScanBookedFuturesResourceDao;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Service
public class AutoScanBookedFuturesResource extends BaseService {
	
	@Autowired
	static AutoScanBookedFuturesResourceDao dao;
	
	/**
	 * 期货资源处理
	 * @param vin
	 */
	public static void processFutureResource(String vin){
		List<Map> list = dao.queryCommonResourceOrderPayUnconfirmed(vin);
		for (Map<String, Object> map : list) {
			Integer nodeStatus = map.get("NODE_STATUS") == null ? 0 : Integer.parseInt(map
					.get("NODE_STATUS").toString());
			String orderId = map.get("ORDER_ID") == null ? "0" : map.get(
					"ORDER_ID").toString();
			String vehicleId = map.get("VEHICLE_ID") == null ? "0" : map
					.get("VEHICLE_ID").toString();
			String commonalityId = map.get("COMMONALITY_ID") == null ? "0" : map
					.get("COMMONALITY_ID").toString();
			// 车辆状态节点在（ZSHP-->ZVP8间），订单信息取消、车辆返回期货资源池
			if (nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_03)
					|| nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_04)
					|| nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_05)
					|| nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_06)) {
				dao.cancelOrder(orderId, vehicleId,commonalityId);// 订单取消,返回资源池

			}
			// 车辆状态节点大于ZVP8，订单信息取消、期货资源信息取消
			if (nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_07)
					|| nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_13)
					|| nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_14)
					|| nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_08)
					|| nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_11)
					|| nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_18)
					|| nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_19)
					|| nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_20)
					|| nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_10)
					|| nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_12)
					|| nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_15)
					|| nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_16)) {
				dao.cancelOrderAndCommonResource(orderId, vehicleId,commonalityId);
			}
		}
	}

}
