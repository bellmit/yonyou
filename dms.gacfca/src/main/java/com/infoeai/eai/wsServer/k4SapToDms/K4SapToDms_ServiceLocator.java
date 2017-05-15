/**
 * K4SapToDms_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.k4SapToDms;

public class K4SapToDms_ServiceLocator extends org.apache.axis.client.Service implements com.infoeai.eai.wsServer.k4SapToDms.K4SapToDms_Service {

    public K4SapToDms_ServiceLocator() {
    }


    public K4SapToDms_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public K4SapToDms_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for K4SapToDmsSOAP
    private java.lang.String K4SapToDmsSOAP_address = "http://www.example.org/";

    public java.lang.String getK4SapToDmsSOAPAddress() {
        return K4SapToDmsSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String K4SapToDmsSOAPWSDDServiceName = "K4SapToDmsSOAP";

    public java.lang.String getK4SapToDmsSOAPWSDDServiceName() {
        return K4SapToDmsSOAPWSDDServiceName;
    }

    public void setK4SapToDmsSOAPWSDDServiceName(java.lang.String name) {
        K4SapToDmsSOAPWSDDServiceName = name;
    }

    public com.infoeai.eai.wsServer.k4SapToDms.K4SapToDms_PortType getK4SapToDmsSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(K4SapToDmsSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getK4SapToDmsSOAP(endpoint);
    }

    public com.infoeai.eai.wsServer.k4SapToDms.K4SapToDms_PortType getK4SapToDmsSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	com.infoeai.eai.wsServer.k4SapToDms.K4SapToDmsSOAPStub _stub = new com.infoeai.eai.wsServer.k4SapToDms.K4SapToDmsSOAPStub(portAddress, this);
            _stub.setPortName(getK4SapToDmsSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setK4SapToDmsSOAPEndpointAddress(java.lang.String address) {
        K4SapToDmsSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.infoeai.eai.wsServer.k4SapToDms.K4SapToDms_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
            	com.infoeai.eai.wsServer.k4SapToDms.K4SapToDmsSOAPStub _stub = new com.infoeai.eai.wsServer.k4SapToDms.K4SapToDmsSOAPStub(new java.net.URL(K4SapToDmsSOAP_address), this);
                _stub.setPortName(getK4SapToDmsSOAPWSDDServiceName());
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
        if ("K4SapToDmsSOAP".equals(inputPortName)) {
            return getK4SapToDmsSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.example.org/K4SapToDms/", "K4SapToDms");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.example.org/K4SapToDms/", "K4SapToDmsSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("K4SapToDmsSOAP".equals(portName)) {
            setK4SapToDmsSOAPEndpointAddress(address);
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
