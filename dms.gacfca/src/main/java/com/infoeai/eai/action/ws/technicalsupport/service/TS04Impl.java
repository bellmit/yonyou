package com.infoeai.eai.action.ws.technicalsupport.service;

import java.util.List;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.ws.technicalsupport.service.TS04Dao;
import com.infoeai.eai.wsServer.vehicle.TsVehicleVO;

/**
 * DCS->DMSS
 * 车辆信息SOAP
 * @author luoyang
 *
 */
@Service
public class TS04Impl extends BaseService implements TS04 {
	
	private static final Logger logger = LoggerFactory.getLogger(TS04Impl.class);
	
	@Autowired
	TS04Dao dao;

	@Override
	public TsVehicleVO[] getTmVehicleService(String updateDate) throws Exception {
		TsVehicleVO[] vehicleArray = null;
		try {
			beginDbService();
			List<TsVehicleVO> list = dao.getTS04VO(updateDate);
			if(list != null && list.size() > 0 ){
				logger.info("TS04Dao.getTS04VO===============>>list长度: " + list.size());
				vehicleArray = new TsVehicleVO[list.size()];
				vehicleArray = list.toArray(vehicleArray);
			}
			dbService.endTxn(true);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
		} finally {
			Base.detach();
			dbService.clean();
		}
		return vehicleArray;
	}

}
