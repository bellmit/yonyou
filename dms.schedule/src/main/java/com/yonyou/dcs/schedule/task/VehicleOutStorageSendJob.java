package com.yonyou.dcs.schedule.task;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dcs.dao.SaleVehicleShippingDao;
import com.yonyou.dcs.de.SADCS001;
import com.yonyou.dcs.gacfca.SADCS001Cloud;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

/**
 * 车辆发运信息下发定时任务
 * @author 夏威
 * 频次：15m/次
 */
@TxnConn
@Component
@SuppressWarnings("rawtypes")
public class VehicleOutStorageSendJob extends TenantSingletonTask {
	
	private static Logger logger = LoggerFactory.getLogger(VehicleOutStorageSendJob.class);
	@Autowired
	private SaleVehicleShippingDao dao;
	
	@Autowired SADCS001 osc;
	
	@Autowired SADCS001Cloud cloud;

	@Override
	public  void execute() throws Exception {
		try {
			logger.info("==========车辆发运信息下发VehicleOutStorageSendJob开始==========");
			List<Map> dlrVhcMap = dao.getSentDealerList();
			
			if (dlrVhcMap == null || dlrVhcMap.size() <= 0) {
				logger.info("==========没有未下发的车辆发运信息==========");
			}
			for (int i = 0; i < dlrVhcMap.size(); i++) {
				Map map = dlrVhcMap.get(i);
				
				String dealerCode = map.get("DEALER_CODE").toString();
				String dealerId = map.get("DEALER_ID").toString();
				String vehicleId = map.get("VEHICLE_ID").toString();
				//区分是调用新接口还是老接口
				if(1==dao.getSendType(dealerCode)){
					osc.sendData(dealerCode, dealerId, vehicleId);
				}else{
					cloud.sendData(dealerCode, dealerId, vehicleId);
				}
			}
			logger.info("==========车辆发运信息下发VehicleOutStorageSendJob结束==========");
			
		} catch (Exception e) {
			logger.error("服务车辆完工下发失败", e);
		}
	}
}
