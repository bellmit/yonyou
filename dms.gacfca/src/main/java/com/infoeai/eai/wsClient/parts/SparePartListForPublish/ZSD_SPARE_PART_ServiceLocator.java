/**
 * ZSD_SPARE_PART_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.SparePartListForPublish;

import java.io.IOException;
import java.util.Properties;

public class ZSD_SPARE_PART_ServiceLocator extends org.apache.axis.client.Service implements com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_Service {

    public ZSD_SPARE_PART_ServiceLocator() {
    }


    public ZSD_SPARE_PART_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ZSD_SPARE_PART_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ZSD_SPARE_PART
    private java.lang.String ZSD_SPARE_PART_address;

    public java.lang.String getZSD_SPARE_PARTAddress() {
        return ZSD_SPARE_PART_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ZSD_SPARE_PARTWSDDServiceName = "ZSD_SPARE_PART";

    public java.lang.String getZSD_SPARE_PARTWSDDServiceName() {
        return ZSD_SPARE_PARTWSDDServiceName;
    }

    public void setZSD_SPARE_PARTWSDDServiceName(java.lang.String name) {
        ZSD_SPARE_PARTWSDDServiceName = name;
    }

    public com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_PortType getZSD_SPARE_PART() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
        	Properties props = new Properties();
        	props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sapuserinfo.properties"));
			String ipport = props.getProperty("ipport");
			String code = props.getProperty("code");
        	ZSD_SPARE_PART_address = "http://" + ipport + "/sap/bc/srt/rfc/sap/zsd_spare_part/" + code + "/zsd_spare_part/zsd_spare_part";
			//ZSD_SPARE_PART_address = "http://10.27.207.176:8081/wsProxy/DCS_SAP_JOB00032";
            endpoint = new java.net.URL(ZSD_SPARE_PART_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        } catch (IOException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
        return getZSD_SPARE_PART(endpoint);
    }

    public com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_PortType getZSD_SPARE_PART(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_BindingStub _stub = new com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_BindingStub(portAddress, this);
            _stub.setPortName(getZSD_SPARE_PARTWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setZSD_SPARE_PARTEndpointAddress(java.lang.String address) {
        ZSD_SPARE_PART_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_BindingStub _stub = new com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_BindingStub(new java.net.URL(ZSD_SPARE_PART_address), this);
                _stub.setPortName(getZSD_SPARE_PARTWSDDServiceName());
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
        if ("ZSD_SPARE_PART".equals(inputPortName)) {
            return getZSD_SPARE_PART();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZSD_SPARE_PART");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZSD_SPARE_PART"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ZSD_SPARE_PART".equals(portName)) {
            setZSD_SPARE_PARTEndpointAddress(address);
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
