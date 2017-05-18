/**
 * ZDMS_L01_RFC_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.ZDMS_L01_RFC;

import java.io.IOException;
import java.util.Properties;

public class ZDMS_L01_RFC_ServiceLocator extends org.apache.axis.client.Service implements com.infoeai.eai.wsClient.parts.ZDMS_L01_RFC.ZDMS_L01_RFC_Service {

    public ZDMS_L01_RFC_ServiceLocator() {
    }


    public ZDMS_L01_RFC_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ZDMS_L01_RFC_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ZDMS_L01_RFC
    private java.lang.String ZDMS_L01_RFC_address;

    public java.lang.String getZDMS_L01_RFCAddress() {
        return ZDMS_L01_RFC_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ZDMS_L01_RFCWSDDServiceName = "ZDMS_L01_RFC";

    public java.lang.String getZDMS_L01_RFCWSDDServiceName() {
        return ZDMS_L01_RFCWSDDServiceName;
    }

    public void setZDMS_L01_RFCWSDDServiceName(java.lang.String name) {
        ZDMS_L01_RFCWSDDServiceName = name;
    }

    public com.infoeai.eai.wsClient.parts.ZDMS_L01_RFC.ZDMS_L01_RFC_PortType getZDMS_L01_RFC() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
        	Properties props = new Properties();
        	props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sapuserinfo.properties"));
        	String ipport = props.getProperty("ipport");;
        	String code = props.getProperty("code");
        	ZDMS_L01_RFC_address = "http://"+ipport+"/sap/bc/srt/rfc/sap/zdms_l01_rfc/"+code+"/zdms_l01_rfc/zdms_l01_rfc";
            endpoint = new java.net.URL(ZDMS_L01_RFC_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        } catch (IOException e) {
        	throw new javax.xml.rpc.ServiceException(e);
		}
        return getZDMS_L01_RFC(endpoint);
    }

    public com.infoeai.eai.wsClient.parts.ZDMS_L01_RFC.ZDMS_L01_RFC_PortType getZDMS_L01_RFC(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	com.infoeai.eai.wsClient.parts.ZDMS_L01_RFC.ZDMS_L01_RFC_BindingStub _stub = new com.infoeai.eai.wsClient.parts.ZDMS_L01_RFC.ZDMS_L01_RFC_BindingStub(portAddress, this);
            _stub.setPortName(getZDMS_L01_RFCWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setZDMS_L01_RFCEndpointAddress(java.lang.String address) {
        ZDMS_L01_RFC_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.infoeai.eai.wsClient.parts.ZDMS_L01_RFC.ZDMS_L01_RFC_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
            	com.infoeai.eai.wsClient.parts.ZDMS_L01_RFC.ZDMS_L01_RFC_BindingStub _stub = new com.infoeai.eai.wsClient.parts.ZDMS_L01_RFC.ZDMS_L01_RFC_BindingStub(new java.net.URL(ZDMS_L01_RFC_address), this);
                _stub.setPortName(getZDMS_L01_RFCWSDDServiceName());
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
        if ("ZDMS_L01_RFC".equals(inputPortName)) {
            return getZDMS_L01_RFC();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZDMS_L01_RFC");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZDMS_L01_RFC"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ZDMS_L01_RFC".equals(portName)) {
            setZDMS_L01_RFCEndpointAddress(address);
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
