/**
 * OrderServicePortServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.bsuv.bs;

public class OrderServicePortServiceLocator extends org.apache.axis.client.Service implements OrderServicePortService {

    public OrderServicePortServiceLocator() {
    }


    public OrderServicePortServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public OrderServicePortServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for OrderServicePortSoap11
    private java.lang.String OrderServicePortSoap11_address = "http://116.90.81.17:8000/wsapi";

    public java.lang.String getOrderServicePortSoap11Address() {
        return OrderServicePortSoap11_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String OrderServicePortSoap11WSDDServiceName = "OrderServicePortSoap11";

    public java.lang.String getOrderServicePortSoap11WSDDServiceName() {
        return OrderServicePortSoap11WSDDServiceName;
    }

    public void setOrderServicePortSoap11WSDDServiceName(java.lang.String name) {
        OrderServicePortSoap11WSDDServiceName = name;
    }

    public OrderServicePort getOrderServicePortSoap11() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(OrderServicePortSoap11_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getOrderServicePortSoap11(endpoint);
    }

    public OrderServicePort getOrderServicePortSoap11(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            OrderServicePortSoap11Stub _stub = new OrderServicePortSoap11Stub(portAddress, this);
            _stub.setPortName(getOrderServicePortSoap11WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setOrderServicePortSoap11EndpointAddress(java.lang.String address) {
        OrderServicePortSoap11_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (OrderServicePort.class.isAssignableFrom(serviceEndpointInterface)) {
                OrderServicePortSoap11Stub _stub = new OrderServicePortSoap11Stub(new java.net.URL(OrderServicePortSoap11_address), this);
                _stub.setPortName(getOrderServicePortSoap11WSDDServiceName());
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
        if ("OrderServicePortSoap11".equals(inputPortName)) {
            return getOrderServicePortSoap11();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://models.generate.boldseas.com", "OrderServicePortService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "OrderServicePortSoap11"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("OrderServicePortSoap11".equals(portName)) {
            setOrderServicePortSoap11EndpointAddress(address);
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
