/**
 * LmsService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.SI12;

public class LmsService_ServiceLocator extends org.apache.axis.client.Service implements com.infoeai.eai.wsServer.SI12.LmsService_Service {

    public LmsService_ServiceLocator() {
    }


    public LmsService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LmsService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for lmsServiceSOAP
    private java.lang.String lmsServiceSOAP_address = "https://dcs.chrysler.com.cn/CGCSL/services/lmsServiceSOAP";

    public java.lang.String getlmsServiceSOAPAddress() {
        return lmsServiceSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String lmsServiceSOAPWSDDServiceName = "lmsServiceSOAP";

    public java.lang.String getlmsServiceSOAPWSDDServiceName() {
        return lmsServiceSOAPWSDDServiceName;
    }

    public void setlmsServiceSOAPWSDDServiceName(java.lang.String name) {
        lmsServiceSOAPWSDDServiceName = name;
    }

    public com.infoeai.eai.wsServer.SI12.LmsService_PortType getlmsServiceSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(lmsServiceSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getlmsServiceSOAP(endpoint);
    }

    public com.infoeai.eai.wsServer.SI12.LmsService_PortType getlmsServiceSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.infoeai.eai.wsServer.SI12.LmsServiceSOAPStub _stub = new com.infoeai.eai.wsServer.SI12.LmsServiceSOAPStub(portAddress, this);
            _stub.setPortName(getlmsServiceSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setlmsServiceSOAPEndpointAddress(java.lang.String address) {
        lmsServiceSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.infoeai.eai.wsServer.SI12.LmsService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.infoeai.eai.wsServer.SI12.LmsServiceSOAPStub _stub = new com.infoeai.eai.wsServer.SI12.LmsServiceSOAPStub(new java.net.URL(lmsServiceSOAP_address), this);
                _stub.setPortName(getlmsServiceSOAPWSDDServiceName());
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
        if ("lmsServiceSOAP".equals(inputPortName)) {
            return getlmsServiceSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.example.org/lmsService/", "lmsService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.example.org/lmsService/", "lmsServiceSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("lmsServiceSOAP".equals(portName)) {
            setlmsServiceSOAPEndpointAddress(address);
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
