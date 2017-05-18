/**
 * QuerySJVTtPartBuy_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy;

public interface QuerySJVTtPartBuy_PortType extends java.rmi.Remote {
    public com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy.MmQuerySJVTtPartBuyPO[] mmQuerySJVTtPartBuy(java.lang.String entityCode, java.lang.String deliveryOrderNo, java.lang.String transNo, java.lang.String sapOrderNo, java.lang.String partNo, java.lang.String partName) throws java.rmi.RemoteException;
}
