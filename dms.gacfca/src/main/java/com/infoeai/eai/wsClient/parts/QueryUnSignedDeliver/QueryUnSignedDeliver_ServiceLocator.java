/**
 * QueryUnSignedDeliver_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver;

public class QueryUnSignedDeliver_ServiceLocator extends org.apache.axis.client.Service implements com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver.QueryUnSignedDeliver_Service {

    public QueryUnSignedDeliver_ServiceLocator() {
    }


    public QueryUnSignedDeliver_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public QueryUnSignedDeliver_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for QueryUnSignedDeliverSOAP
    private java.lang.String QueryUnSignedDeliverSOAP_address = "http://www.example.org/";

    public java.lang.String getQueryUnSignedDeliverSOAPAddress() {
        return QueryUnSignedDeliverSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String QueryUnSignedDeliverSOAPWSDDServiceName = "QueryUnSignedDeliverSOAP";

    public java.lang.String getQueryUnSignedDeliverSOAPWSDDServiceName() {
        return QueryUnSignedDeliverSOAPWSDDServiceName;
    }

    public void setQueryUnSignedDeliverSOAPWSDDServiceName(java.lang.String name) {
        QueryUnSignedDeliverSOAPWSDDServiceName = name;
    }

    public com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver.QueryUnSignedDeliver_PortType getQueryUnSignedDeliverSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(QueryUnSignedDeliverSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getQueryUnSignedDeliverSOAP(endpoint);
    }

    public com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver.QueryUnSignedDeliver_PortType getQueryUnSignedDeliverSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver.QueryUnSignedDeliverSOAPStub _stub = new com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver.QueryUnSignedDeliverSOAPStub(portAddress, this);
            _stub.setPortName(getQueryUnSignedDeliverSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setQueryUnSignedDeliverSOAPEndpointAddress(java.lang.String address) {
        QueryUnSignedDeliverSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver.QueryUnSignedDeliver_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver.QueryUnSignedDeliverSOAPStub _stub = new com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver.QueryUnSignedDeliverSOAPStub(new java.net.URL(QueryUnSignedDeliverSOAP_address), this);
                _stub.setPortName(getQueryUnSignedDeliverSOAPWSDDServiceName());
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
        if ("QueryUnSignedDeliverSOAP".equals(inputPortName)) {
            return getQueryUnSignedDeliverSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.example.org/QueryUnSignedDeliver/", "QueryUnSignedDeliver");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.example.org/QueryUnSignedDeliver/", "QueryUnSignedDeliverSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("QueryUnSignedDeliverSOAP".equals(portName)) {
            setQueryUnSignedDeliverSOAPEndpointAddress(address);
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
