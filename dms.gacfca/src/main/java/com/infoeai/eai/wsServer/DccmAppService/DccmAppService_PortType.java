/**
 * DccmAppService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.DccmAppService;

public interface DccmAppService_PortType extends java.rmi.Remote {
    public SalesConsultantVo[] getSalesConsultantList(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException;
    public PotentialCustomerVo[] getThePotentialCustomerList(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException;
    public CustomerReceptionVo[] getCustomerReceptionList(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException;
    public CustomerStatusVo[] getCustomerStatusList(java.lang.String from, java.lang.String newElement) throws java.rmi.RemoteException;
    public PotentialCustomerVo[] getThePotentialCustomerEditList(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException;
    public CustomerReceptionVo[] getCustomerReceptionEditList(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException;
}
