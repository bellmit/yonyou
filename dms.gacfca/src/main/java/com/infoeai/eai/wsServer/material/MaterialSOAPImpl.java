/**
 * MaterialSOAPImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.material;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;

import com.infoeai.eai.action.ws.technicalsupport.service.TS05;

public class MaterialSOAPImpl implements com.infoeai.eai.wsServer.material.Material_PortType{	
	@Autowired
	TS05 ts05;
	
    public com.infoeai.eai.wsServer.material.TsMaterialVO[] materialInfo(java.util.Date startDate) throws java.rmi.RemoteException {
//        TsMaterialVO[] tm = {new TsMaterialVO()};
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    	TsMaterialVO[] tm = null;
    	if(startDate != null){
    		try {
				tm = ts05.getTmMaterialService(sf.format(startDate));
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	return tm;
    }

}
