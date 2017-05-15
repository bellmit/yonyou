/**
 * Service4WxSOAPImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.wx.service4wx;

import com.infoeai.eai.action.wx.WX06;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

public class Service4WxSOAPImpl implements
		com.infoeai.eai.wsServer.wx.service4wx.Service4Wx_PortType {
	public java.lang.String customerFeedback(
			com.infoeai.eai.wsServer.wx.service4wx.CustomerFeedbackPO[] in0)
			throws java.rmi.RemoteException {
		WX06 wx06 = (WX06)ApplicationContextHelper.getBeanByType(WX06.class);
		String returnValue = "";
		try {
			returnValue = wx06.execute(in0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}

	public java.lang.String customerBindingFeedback(
			com.infoeai.eai.wsServer.wx.service4wx.CustomerBindingFeedbackPO[] in1)
			throws java.rmi.RemoteException {
//		WX07 wx07 = new WX07();
//		String returnValue = "";
//		try {
//			returnValue = wx07.execute(in1);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return returnValue;
		return null;
	}

}
