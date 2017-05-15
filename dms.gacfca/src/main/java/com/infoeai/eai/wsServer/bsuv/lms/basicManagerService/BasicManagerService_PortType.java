/**
 * BasicManagerService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.bsuv.lms.basicManagerService;

public interface BasicManagerService_PortType extends java.rmi.Remote {
    public com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.MaterialVO[] getMaterialList(java.util.Date from, java.util.Date to) throws java.rmi.RemoteException;
    public com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.DealerVo[] getDealerList() throws java.rmi.RemoteException;
}
