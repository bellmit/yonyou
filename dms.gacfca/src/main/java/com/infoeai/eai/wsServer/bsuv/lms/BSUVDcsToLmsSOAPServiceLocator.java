/**
 * BSUVDcsToLmsSOAPServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.bsuv.lms;

public class BSUVDcsToLmsSOAPServiceLocator extends org.apache.axis.client.Service implements BSUVDcsToLmsSOAPService {

    public BSUVDcsToLmsSOAPServiceLocator() {
    }


    public BSUVDcsToLmsSOAPServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BSUVDcsToLmsSOAPServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BSUVDcsToLmsSOAP
    private java.lang.String BSUVDcsToLmsSOAP_address = "http://localhost:8080/Wb/services/BSUVDcsToLmsSOAP";

    public java.lang.String getBSUVDcsToLmsSOAPAddress() {
        return BSUVDcsToLmsSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BSUVDcsToLmsSOAPWSDDServiceName = "BSUVDcsToLmsSOAP";

    public java.lang.String getBSUVDcsToLmsSOAPWSDDServiceName() {
        return BSUVDcsToLmsSOAPWSDDServiceName;
    }

    public void setBSUVDcsToLmsSOAPWSDDServiceName(java.lang.String name) {
        BSUVDcsToLmsSOAPWSDDServiceName = name;
    }

    public BSUVDcsToLmsSOAP getBSUVDcsToLmsSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BSUVDcsToLmsSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBSUVDcsToLmsSOAP(endpoint);
    }

    public BSUVDcsToLmsSOAP getBSUVDcsToLmsSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            BSUVDcsToLmsSOAPSoapBindingStub _stub = new BSUVDcsToLmsSOAPSoapBindingStub(portAddress, this);
            _stub.setPortName(getBSUVDcsToLmsSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBSUVDcsToLmsSOAPEndpointAddress(java.lang.String address) {
        BSUVDcsToLmsSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (BSUVDcsToLmsSOAP.class.isAssignableFrom(serviceEndpointInterface)) {
                BSUVDcsToLmsSOAPSoapBindingStub _stub = new BSUVDcsToLmsSOAPSoapBindingStub(new java.net.URL(BSUVDcsToLmsSOAP_address), this);
                _stub.setPortName(getBSUVDcsToLmsSOAPWSDDServiceName());
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
        if ("BSUVDcsToLmsSOAP".equals(inputPortName)) {
            return getBSUVDcsToLmsSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.example.org", "BSUVDcsToLmsSOAPService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.example.org", "BSUVDcsToLmsSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BSUVDcsToLmsSOAP".equals(portName)) {
            setBSUVDcsToLmsSOAPEndpointAddress(address);
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
