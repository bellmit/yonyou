/**
 * ZRWS_PARTS_STOCK_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.ecPartStock;

import java.io.IOException;
import java.util.Properties;

public class ZRWS_PARTS_STOCK_ServiceLocator extends org.apache.axis.client.Service implements com.infoeai.eai.wsClient.parts.ecPartStock.ZRWS_PARTS_STOCK_Service {

    public ZRWS_PARTS_STOCK_ServiceLocator() {
    }


    public ZRWS_PARTS_STOCK_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ZRWS_PARTS_STOCK_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ZRWS_PARTS_STOCK
    private java.lang.String ZRWS_PARTS_STOCK_address;

    public java.lang.String getZRWS_PARTS_STOCKAddress() {
        return ZRWS_PARTS_STOCK_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ZRWS_PARTS_STOCKWSDDServiceName = "ZRWS_PARTS_STOCK";

    public java.lang.String getZRWS_PARTS_STOCKWSDDServiceName() {
        return ZRWS_PARTS_STOCKWSDDServiceName;
    }

    public void setZRWS_PARTS_STOCKWSDDServiceName(java.lang.String name) {
        ZRWS_PARTS_STOCKWSDDServiceName = name;
    }

    public com.infoeai.eai.wsClient.parts.ecPartStock.ZRWS_PARTS_STOCK_PortType getZRWS_PARTS_STOCK() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
        	Properties props = new Properties();
        	props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sapuserinfo.properties"));
        	String ipport = props.getProperty("ipport");;
        	String code = props.getProperty("code");;
        	ZRWS_PARTS_STOCK_address = "http://"+ipport+"/sap/bc/srt/rfc/sap/zrws_parts_stock/"+code+"/zrws_parts_stock/zrws_parts_stock";	
            endpoint = new java.net.URL(ZRWS_PARTS_STOCK_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        } catch (IOException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
        return getZRWS_PARTS_STOCK(endpoint);
    }

    public com.infoeai.eai.wsClient.parts.ecPartStock.ZRWS_PARTS_STOCK_PortType getZRWS_PARTS_STOCK(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.infoeai.eai.wsClient.parts.ecPartStock.ZRWS_PARTS_STOCK_BindingStub _stub = new com.infoeai.eai.wsClient.parts.ecPartStock.ZRWS_PARTS_STOCK_BindingStub(portAddress, this);
            _stub.setPortName(getZRWS_PARTS_STOCKWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setZRWS_PARTS_STOCKEndpointAddress(java.lang.String address) {
        ZRWS_PARTS_STOCK_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.infoeai.eai.wsClient.parts.ecPartStock.ZRWS_PARTS_STOCK_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.infoeai.eai.wsClient.parts.ecPartStock.ZRWS_PARTS_STOCK_BindingStub _stub = new com.infoeai.eai.wsClient.parts.ecPartStock.ZRWS_PARTS_STOCK_BindingStub(new java.net.URL(ZRWS_PARTS_STOCK_address), this);
                _stub.setPortName(getZRWS_PARTS_STOCKWSDDServiceName());
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
        if ("ZRWS_PARTS_STOCK".equals(inputPortName)) {
            return getZRWS_PARTS_STOCK();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZRWS_PARTS_STOCK");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZRWS_PARTS_STOCK"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ZRWS_PARTS_STOCK".equals(portName)) {
            setZRWS_PARTS_STOCKEndpointAddress(address);
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
