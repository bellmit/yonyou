/**
 * DccmAppServiceSOAPImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.DccmAppService;

import com.infoeai.eai.action.bsuv.dccmApp.DCSTODCCM003;
import com.infoeai.eai.action.bsuv.dccmApp.DCSTODCCM004;
import com.infoeai.eai.action.bsuv.dccmApp.DCSTODCCM005;
import com.infoeai.eai.action.bsuv.dccmApp.DCSTODCCM006;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

public class DccmAppServiceSOAPImpl implements DccmAppService_PortType{
	
	/**
	 * 获取销售顾问信息
	 */
    public SalesConsultantVo[] getSalesConsultantList(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException {
    	DCSTODCCM003 d3 = (DCSTODCCM003)ApplicationContextHelper.getBeanByType(DCSTODCCM003.class);
    	SalesConsultantVo[] result = null;
        try {
        	result = d3.getSalesConsultantList(from, to);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
    }
    
    /**
     * 获取潜客信息
     */
    public PotentialCustomerVo[] getThePotentialCustomerList(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException {
    	DCSTODCCM004 d4 = (DCSTODCCM004)ApplicationContextHelper.getBeanByType(DCSTODCCM004.class);
    	PotentialCustomerVo[] result = null;
    	try {
			result = d4.getThePotentialCustomerList(from, to);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return result;
    }
    
    /**
     * 获取客户接待/沟通数据
     */
    public CustomerReceptionVo[] getCustomerReceptionList(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException {
    	DCSTODCCM005 d5 = (DCSTODCCM005)ApplicationContextHelper.getBeanByType(DCSTODCCM005.class);
    	CustomerReceptionVo[] result = null;
    	try {
			result = d5.getCustomerReceptionList(from, to);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return result;
    }
    
    /**
     * 获取休眠、订单、交车客户状态
     */
    public CustomerStatusVo[] getCustomerStatusList(java.lang.String from, java.lang.String newElement) throws java.rmi.RemoteException {
    	DCSTODCCM006 d6 = (DCSTODCCM006)ApplicationContextHelper.getBeanByType(DCSTODCCM006.class);
    	CustomerStatusVo[] result = null;
    	try {
			result = d6.getCustomerStatusList(from, newElement);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return result;
    }

    /**
     * 获取潜客信息（修改）
     */
    public PotentialCustomerVo[] getThePotentialCustomerEditList(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException {
//        return d4u.getThePotentialCustomerEditList(from, to);
    	 return null;
    }

    /**
     * 获取客户接待/沟通数据（修改）
     */
    public CustomerReceptionVo[] getCustomerReceptionEditList(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException {
//        return d5u.getCustomerReceptionEditList(from, to);
    	 return null;
    }

}
