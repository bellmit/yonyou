/**
 * SingleQueryLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.lms.singlequery;

import java.rmi.Remote;

@SuppressWarnings("serial")
public class SingleQueryLocator extends org.apache.axis.client.Service implements com.infoeai.eai.wsClient.lms.singlequery.SingleQuery {

    public SingleQueryLocator() {
    }


    public SingleQueryLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SingleQueryLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SingleQuerySoap
    private java.lang.String SingleQuerySoap_address = "http://testdmsws.jeepsupport.com.cn/SingleQuery.asmx";

    public java.lang.String getSingleQuerySoapAddress() {
        return SingleQuerySoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SingleQuerySoapWSDDServiceName = "SingleQuerySoap";

    public java.lang.String getSingleQuerySoapWSDDServiceName() {
        return SingleQuerySoapWSDDServiceName;
    }

    public void setSingleQuerySoapWSDDServiceName(java.lang.String name) {
        SingleQuerySoapWSDDServiceName = name;
    }

    public com.infoeai.eai.wsClient.lms.singlequery.SingleQuerySoap getSingleQuerySoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SingleQuerySoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSingleQuerySoap(endpoint);
    }

    public com.infoeai.eai.wsClient.lms.singlequery.SingleQuerySoap getSingleQuerySoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.infoeai.eai.wsClient.lms.singlequery.SingleQuerySoapStub _stub = new com.infoeai.eai.wsClient.lms.singlequery.SingleQuerySoapStub(portAddress, this);
            _stub.setPortName(getSingleQuerySoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSingleQuerySoapEndpointAddress(java.lang.String address) {
        SingleQuerySoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.infoeai.eai.wsClient.lms.singlequery.SingleQuerySoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.infoeai.eai.wsClient.lms.singlequery.SingleQuerySoapStub _stub = new com.infoeai.eai.wsClient.lms.singlequery.SingleQuerySoapStub(new java.net.URL(SingleQuerySoap_address), this);
                _stub.setPortName(getSingleQuerySoapWSDDServiceName());
                return (Remote) _stub;
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
        if ("SingleQuerySoap".equals(inputPortName)) {
            return (Remote) getSingleQuerySoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "SingleQuery");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "SingleQuerySoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SingleQuerySoap".equals(portName)) {
            setSingleQuerySoapEndpointAddress(address);
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
