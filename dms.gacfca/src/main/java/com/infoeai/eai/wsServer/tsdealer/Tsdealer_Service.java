/**
 * Tsdealer_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.tsdealer;

public interface Tsdealer_Service extends javax.xml.rpc.Service {
    public java.lang.String getdealerSOAPAddress();

    public com.infoeai.eai.wsServer.tsdealer.Tsdealer_PortType getdealerSOAP() throws javax.xml.rpc.ServiceException;

    public com.infoeai.eai.wsServer.tsdealer.Tsdealer_PortType getdealerSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
