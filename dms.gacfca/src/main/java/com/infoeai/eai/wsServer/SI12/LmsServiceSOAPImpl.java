/**
 * LmsServiceSOAPImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.SI12;

import com.infoeai.eai.action.lms.SI12;
import com.infoeai.eai.action.lms.SI29;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

public class LmsServiceSOAPImpl implements com.infoeai.eai.wsServer.SI12.LmsService_PortType{
    public java.lang.String getSalesCustInfo(com.infoeai.eai.wsServer.SI12.InfoPO[] in0) throws java.rmi.RemoteException {
    	SI12 si12 = (SI12)ApplicationContextHelper.getBeanByType(SI12.class);
        String returnValue = "";
        try {
			returnValue = si12.execute(in0);
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }
        return returnValue;
    }

    public java.lang.String getCreateSalesCustInfo(com.infoeai.eai.wsServer.SI12.CreateInfoPO[] in) throws java.rmi.RemoteException {
    	SI29 si29 = (SI29)ApplicationContextHelper.getBeanByType(SI29.class);
        String returnValue = "";
        try {
			returnValue = si29.execute(in);
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }
        return returnValue;
    }

}
