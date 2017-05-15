/**
 * CancelOrderService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.cancelOrder;

public class CancelOrderService_ServiceLocator extends org.apache.axis.client.Service implements CancelOrderService_Service {

    public CancelOrderService_ServiceLocator() {
    }


    public CancelOrderService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CancelOrderService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for cancelOrderServiceSOAP
    private java.lang.String cancelOrderServiceSOAP_address = "http://www.example.org/";

    public java.lang.String getcancelOrderServiceSOAPAddress() {
        return cancelOrderServiceSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String cancelOrderServiceSOAPWSDDServiceName = "cancelOrderServiceSOAP";

    public java.lang.String getcancelOrderServiceSOAPWSDDServiceName() {
        return cancelOrderServiceSOAPWSDDServiceName;
    }

    public void setcancelOrderServiceSOAPWSDDServiceName(java.lang.String name) {
        cancelOrderServiceSOAPWSDDServiceName = name;
    }

    public CancelOrderService_PortType getcancelOrderServiceSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(cancelOrderServiceSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getcancelOrderServiceSOAP(endpoint);
    }

    public CancelOrderService_PortType getcancelOrderServiceSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            CancelOrderServiceSOAPStub _stub = new CancelOrderServiceSOAPStub(portAddress, this);
            _stub.setPortName(getcancelOrderServiceSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setcancelOrderServiceSOAPEndpointAddress(java.lang.String address) {
        cancelOrderServiceSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (CancelOrderService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                CancelOrderServiceSOAPStub _stub = new CancelOrderServiceSOAPStub(new java.net.URL(cancelOrderServiceSOAP_address), this);
                _stub.setPortName(getcancelOrderServiceSOAPWSDDServiceName());
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
        if ("cancelOrderServiceSOAP".equals(inputPortName)) {
            return getcancelOrderServiceSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.example.org/cancelOrderService/", "cancelOrderService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.example.org/cancelOrderService/", "cancelOrderServiceSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("cancelOrderServiceSOAP".equals(portName)) {
            setcancelOrderServiceSOAPEndpointAddress(address);
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
