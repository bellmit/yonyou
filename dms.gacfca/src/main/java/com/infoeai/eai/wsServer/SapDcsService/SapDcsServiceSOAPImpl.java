/**
 * SapDcsServiceSOAPImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.SapDcsService;

import com.infoeai.eai.action.ncserp.SI01v2;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

public class SapDcsServiceSOAPImpl implements
		com.infoeai.eai.wsServer.SapDcsService.SapDcsService_PortType {
	
	public java.lang.String execute(
			com.infoeai.eai.wsServer.SapDcsService.SapOutboundVO[] in0)
			throws java.rmi.RemoteException {
		SI01v2 si01v2 = (SI01v2)ApplicationContextHelper.getBeanByType(SI01v2.class);
        String returnValue = "";
        try {
			returnValue = si01v2.execute(in0);
		 } catch (Exception e) {
				e.printStackTrace();
		 }
        return returnValue;
		
	}

}
