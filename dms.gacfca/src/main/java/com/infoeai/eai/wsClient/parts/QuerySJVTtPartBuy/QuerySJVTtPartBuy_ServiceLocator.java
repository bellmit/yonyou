/**
 * QuerySJVTtPartBuy_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy;

public class QuerySJVTtPartBuy_ServiceLocator extends org.apache.axis.client.Service implements com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy.QuerySJVTtPartBuy_Service {

    public QuerySJVTtPartBuy_ServiceLocator() {
    }


    public QuerySJVTtPartBuy_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public QuerySJVTtPartBuy_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for QuerySJVTtPartBuySOAP
    private java.lang.String QuerySJVTtPartBuySOAP_address = "http://www.example.org/";

    public java.lang.String getQuerySJVTtPartBuySOAPAddress() {
        return QuerySJVTtPartBuySOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String QuerySJVTtPartBuySOAPWSDDServiceName = "QuerySJVTtPartBuySOAP";

    public java.lang.String getQuerySJVTtPartBuySOAPWSDDServiceName() {
        return QuerySJVTtPartBuySOAPWSDDServiceName;
    }

    public void setQuerySJVTtPartBuySOAPWSDDServiceName(java.lang.String name) {
        QuerySJVTtPartBuySOAPWSDDServiceName = name;
    }

    public com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy.QuerySJVTtPartBuy_PortType getQuerySJVTtPartBuySOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(QuerySJVTtPartBuySOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getQuerySJVTtPartBuySOAP(endpoint);
    }

    public com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy.QuerySJVTtPartBuy_PortType getQuerySJVTtPartBuySOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy.QuerySJVTtPartBuySOAPStub _stub = new com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy.QuerySJVTtPartBuySOAPStub(portAddress, this);
            _stub.setPortName(getQuerySJVTtPartBuySOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setQuerySJVTtPartBuySOAPEndpointAddress(java.lang.String address) {
        QuerySJVTtPartBuySOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy.QuerySJVTtPartBuy_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy.QuerySJVTtPartBuySOAPStub _stub = new com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy.QuerySJVTtPartBuySOAPStub(new java.net.URL(QuerySJVTtPartBuySOAP_address), this);
                _stub.setPortName(getQuerySJVTtPartBuySOAPWSDDServiceName());
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
        if ("QuerySJVTtPartBuySOAP".equals(inputPortName)) {
            return getQuerySJVTtPartBuySOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "QuerySJVTtPartBuy");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "QuerySJVTtPartBuySOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("QuerySJVTtPartBuySOAP".equals(portName)) {
            setQuerySJVTtPartBuySOAPEndpointAddress(address);
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
