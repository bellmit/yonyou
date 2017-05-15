package com.infoeai.eai.action.ws.technicalsupport.service;

import com.infoeai.eai.wsServer.vehicle.TsVehicleVO;

public interface TS04 {
	
	public TsVehicleVO[] getTmVehicleService(String updateDate) throws Exception;

}
