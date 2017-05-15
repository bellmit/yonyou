/**
 * DMSWebserviceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.lms.dmswebservice;

public interface DMSWebserviceSoap extends java.rmi.Remote {

    /**
     * DMS反馈的销售线索状态
     */
    public int backSaleslead(DCC_DMSStatus salesLead) throws java.rmi.RemoteException;

    /**
     * 车型主数据信息接口
     */
    public int DMSProductInfo(DCC_DMSProduct dmsProduct) throws java.rmi.RemoteException;

    /**
     * 经销商主数据接口
     */
    public int DMSDealerInfo(DCC_DMSDealer dmsDealer) throws java.rmi.RemoteException;

    /**
     * 测试IsValide
     */
    public java.lang.String textValide() throws java.rmi.RemoteException;
}
