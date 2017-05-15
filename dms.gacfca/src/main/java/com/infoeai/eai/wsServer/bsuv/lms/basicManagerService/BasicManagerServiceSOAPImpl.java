/**
 * BasicManagerServiceSOAPImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.bsuv.lms.basicManagerService;

import org.springframework.stereotype.Controller;

import com.infoeai.eai.action.bsuv.lms.DCSTOLMS001;
import com.infoeai.eai.action.bsuv.lms.DCSTOLMS002;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

@Controller
public class BasicManagerServiceSOAPImpl implements  com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.BasicManagerService_PortType{
	

	public com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.MaterialVO[] getMaterialList(java.util.Date from, java.util.Date to) throws java.rmi.RemoteException {
		DCSTOLMS001 service = (DCSTOLMS001)ApplicationContextHelper.getBeanByType(DCSTOLMS001.class);
		MaterialVO[] voList = null;
		try {
			voList = service.execute(from, to);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return voList;
    }

    public com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.DealerVo[] getDealerList() throws java.rmi.RemoteException {
    	DCSTOLMS002 service = (DCSTOLMS002)ApplicationContextHelper.getBeanByType(DCSTOLMS002.class);
        DealerVo[] voList = null;
		try {
			voList = service.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return voList;
    }

}
