/**
 * BSUVDcsToLmsSOAPSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.bsuv.lms;

import org.apache.log4j.Logger;

import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

public class BSUVDcsToLmsSOAPSoapBindingImpl implements BSUVDcsToLmsSOAP{
	private static Logger logger = Logger.getLogger(BSUVDcsToLmsSOAPSoapBindingImpl.class);
	
    public EcCheckFeedBackVO[] DCSTOLMS007(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException {
        com.infoeai.eai.action.bsuv.lms.DCSTOLMS007 bsuvdcstolms007 = 
        		(com.infoeai.eai.action.bsuv.lms.DCSTOLMS007)ApplicationContextHelper.getBeanByType(com.infoeai.eai.action.bsuv.lms.DCSTOLMS007.class);
        try {
			logger.info("======================日期格式合规定======from==:="+from+"==to:==="+to+"====");
			return bsuvdcstolms007.sendBSUVCueCheckResult(from, to);
		} catch (Exception e) {
			logger.info("======================日期格式不合规定=====from==:="+from+"==to:==="+to+"=====");
			return null;
		} 
    }

    public TrailCustVO[] DCSTOLMS008(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException {
    	com.infoeai.eai.action.bsuv.lms.DCSTOLMS008Impl bsuvdcstolms008 = 
        		(com.infoeai.eai.action.bsuv.lms.DCSTOLMS008Impl)ApplicationContextHelper.getBeanByType(com.infoeai.eai.action.bsuv.lms.DCSTOLMS008Impl.class);
        try {
			logger.info("======================日期格式合规定=====from==:="+from+"==to:==="+to+"=======");
			return bsuvdcstolms008.sendBSUVCustFollowUpInfo(from, to);
		} catch (Exception e) {
			logger.info("======================日期格式不合规定====from==:="+from+"==to:==="+to+"=======");
			return null;
		} 

    }

}
