/**
 * DMSWebservice.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.lms.dmswebservice;

public interface DMSWebservice extends javax.xml.rpc.Service {
    public java.lang.String getDMSWebserviceSoapAddress();

    public DMSWebserviceSoap getDMSWebserviceSoap() throws javax.xml.rpc.ServiceException;

    public DMSWebserviceSoap getDMSWebserviceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
