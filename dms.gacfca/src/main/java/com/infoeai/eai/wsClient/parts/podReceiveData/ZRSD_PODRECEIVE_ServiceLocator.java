/**
 * ZRSD_PODRECEIVE_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.podReceiveData;

import java.io.IOException;
import java.util.Properties;

public class ZRSD_PODRECEIVE_ServiceLocator extends org.apache.axis.client.Service implements com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_Service {

    public ZRSD_PODRECEIVE_ServiceLocator() {
    }


    public ZRSD_PODRECEIVE_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ZRSD_PODRECEIVE_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ZRSD_PODRECEIVE
    private java.lang.String ZRSD_PODRECEIVE_address;

    public java.lang.String getZRSD_PODRECEIVEAddress() {
        return ZRSD_PODRECEIVE_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ZRSD_PODRECEIVEWSDDServiceName = "ZRSD_PODRECEIVE";

    public java.lang.String getZRSD_PODRECEIVEWSDDServiceName() {
        return ZRSD_PODRECEIVEWSDDServiceName;
    }

    public void setZRSD_PODRECEIVEWSDDServiceName(java.lang.String name) {
        ZRSD_PODRECEIVEWSDDServiceName = name;
    }

    public com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_PortType getZRSD_PODRECEIVE() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
        	Properties props = new Properties();
        	props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sapuserinfo.properties"));
			String ipport = props.getProperty("ipport");
			String code = props.getProperty("code");
        	ZRSD_PODRECEIVE_address = "http://" + ipport + "/sap/bc/srt/rfc/sap/zrsd_podreceive/" + code + "/zrsd_podreceive/zrsd_podreceive";
        	//ZRSD_PODRECEIVE_address = "http://10.27.207.176:8081/wsProxy/DCS_SAP_JOB00033";
            endpoint = new java.net.URL(ZRSD_PODRECEIVE_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        } catch (IOException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
        return getZRSD_PODRECEIVE(endpoint);
    }

    public com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_PortType getZRSD_PODRECEIVE(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_BindingStub _stub = new com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_BindingStub(portAddress, this);
            _stub.setPortName(getZRSD_PODRECEIVEWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setZRSD_PODRECEIVEEndpointAddress(java.lang.String address) {
        ZRSD_PODRECEIVE_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_BindingStub _stub = new com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_BindingStub(new java.net.URL(ZRSD_PODRECEIVE_address), this);
                _stub.setPortName(getZRSD_PODRECEIVEWSDDServiceName());
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
        if ("ZRSD_PODRECEIVE".equals(inputPortName)) {
            return getZRSD_PODRECEIVE();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZRSD_PODRECEIVE");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZRSD_PODRECEIVE"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ZRSD_PODRECEIVE".equals(portName)) {
            setZRSD_PODRECEIVEEndpointAddress(address);
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
