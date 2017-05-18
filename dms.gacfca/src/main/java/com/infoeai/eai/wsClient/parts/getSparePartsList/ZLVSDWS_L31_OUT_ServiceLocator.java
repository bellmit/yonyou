/**
 * ZLVSDWS_L31_OUT_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.getSparePartsList;

import java.io.IOException;
import java.util.Properties;

public class ZLVSDWS_L31_OUT_ServiceLocator extends org.apache.axis.client.Service implements com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_Service {

    public ZLVSDWS_L31_OUT_ServiceLocator() {
    }


    public ZLVSDWS_L31_OUT_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ZLVSDWS_L31_OUT_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ZLVSDWS_L31_OUT
    private java.lang.String ZLVSDWS_L31_OUT_address;

    public java.lang.String getZLVSDWS_L31_OUTAddress() {
        return ZLVSDWS_L31_OUT_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ZLVSDWS_L31_OUTWSDDServiceName = "ZLVSDWS_L31_OUT";

    public java.lang.String getZLVSDWS_L31_OUTWSDDServiceName() {
        return ZLVSDWS_L31_OUTWSDDServiceName;
    }

    public void setZLVSDWS_L31_OUTWSDDServiceName(java.lang.String name) {
        ZLVSDWS_L31_OUTWSDDServiceName = name;
    }

    public com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_PortType getZLVSDWS_L31_OUT() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
        	Properties props = new Properties();
        	props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sapuserinfo.properties"));
			String ipport = props.getProperty("ipport");
			String code = props.getProperty("code");
        	ZLVSDWS_L31_OUT_address = "http://" + ipport + "/sap/bc/srt/rfc/sap/zlvsdws_l31_out/" + code + "/zlvsdws_l31_out/zlvsdws_l31_out";
        	//ZLVSDWS_L31_OUT_address = "http://10.27.207.176:8081/wsProxy/DCS_SAP_JOB00024";
            endpoint = new java.net.URL(ZLVSDWS_L31_OUT_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        } catch (IOException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
        return getZLVSDWS_L31_OUT(endpoint);
    }

    public com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_PortType getZLVSDWS_L31_OUT(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_BindingStub _stub = new com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_BindingStub(portAddress, this);
            _stub.setPortName(getZLVSDWS_L31_OUTWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setZLVSDWS_L31_OUTEndpointAddress(java.lang.String address) {
        ZLVSDWS_L31_OUT_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_BindingStub _stub = new com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_BindingStub(new java.net.URL(ZLVSDWS_L31_OUT_address), this);
                _stub.setPortName(getZLVSDWS_L31_OUTWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ZLVSDWS_L31_OUT".equals(inputPortName)) {
            return getZLVSDWS_L31_OUT();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZLVSDWS_L31_OUT");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZLVSDWS_L31_OUT"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ZLVSDWS_L31_OUT".equals(portName)) {
            setZLVSDWS_L31_OUTEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
