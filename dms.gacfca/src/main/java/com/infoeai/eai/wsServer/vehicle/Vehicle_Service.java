/**
 * Vehicle_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.vehicle;

public interface Vehicle_Service extends javax.xml.rpc.Service {
    public java.lang.String getvehicleSOAPAddress();

    public com.infoeai.eai.wsServer.vehicle.Vehicle_PortType getvehicleSOAP() throws javax.xml.rpc.ServiceException;

    public com.infoeai.eai.wsServer.vehicle.Vehicle_PortType getvehicleSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
