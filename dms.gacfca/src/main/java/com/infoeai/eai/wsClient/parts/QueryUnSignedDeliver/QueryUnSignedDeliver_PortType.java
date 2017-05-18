/**
 * QueryUnSignedDeliver_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver;

public interface QueryUnSignedDeliver_PortType extends java.rmi.Remote {
    public com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver.MmQueryUnSignedDeliverSAPVO[] mmQueryUnSignedDeliver(java.lang.String entityCode, java.lang.String deliveryOrderNo, java.lang.String partNo, java.lang.String transNo) throws java.rmi.RemoteException;
}
