/**
 * VehicleSOAPImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.vehicle;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;

import com.infoeai.eai.action.ws.technicalsupport.service.TS04;

public class VehicleSOAPImpl implements com.infoeai.eai.wsServer.vehicle.Vehicle_PortType{
	@Autowired
	TS04 ts04;
	
    public com.infoeai.eai.wsServer.vehicle.TsVehicleVO[] vehicleInfo(java.util.Date startDate) throws java.rmi.RemoteException {
//    	TsVehicleVO[] vs = {new TsVehicleVO()};
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    	TsVehicleVO[] vs = null;
    	if(startDate != null){
    		try {
				vs = ts04.getTmVehicleService(sf.format(startDate));
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	return vs;
    }

}
