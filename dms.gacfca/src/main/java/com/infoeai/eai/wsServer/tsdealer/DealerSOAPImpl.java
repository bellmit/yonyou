/**
 * DealerSOAPImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.tsdealer;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;

import com.infoeai.eai.action.ws.technicalsupport.service.TS01;

public class DealerSOAPImpl implements com.infoeai.eai.wsServer.tsdealer.Tsdealer_PortType{
	@Autowired
	TS01 ts01;
	
    public com.infoeai.eai.wsServer.tsdealer.TsDealerVO[] dealerInfo(java.util.Date startDate) throws java.rmi.RemoteException {
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    	TsDealerVO[] ts = null;
    	if(startDate != null){
    		try {
				ts = ts01.getTmDealerService(sf.format(startDate));
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
        return ts;
    }

}
