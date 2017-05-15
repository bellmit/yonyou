/**
 * Tsdealer_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.tsdealer;

public class Tsdealer_ServiceLocator extends org.apache.axis.client.Service implements com.infoeai.eai.wsServer.tsdealer.Tsdealer_Service {

    public Tsdealer_ServiceLocator() {
    }


    public Tsdealer_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Tsdealer_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for dealerSOAP
    private java.lang.String dealerSOAP_address = "http://wsServer.eai.infoeai.com/";

    public java.lang.String getdealerSOAPAddress() {
        return dealerSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String dealerSOAPWSDDServiceName = "dealerSOAP";

    public java.lang.String getdealerSOAPWSDDServiceName() {
        return dealerSOAPWSDDServiceName;
    }

    public void setdealerSOAPWSDDServiceName(java.lang.String name) {
        dealerSOAPWSDDServiceName = name;
    }

    public com.infoeai.eai.wsServer.tsdealer.Tsdealer_PortType getdealerSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(dealerSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getdealerSOAP(endpoint);
    }

    public com.infoeai.eai.wsServer.tsdealer.Tsdealer_PortType getdealerSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.infoeai.eai.wsServer.tsdealer.DealerSOAPStub _stub = new com.infoeai.eai.wsServer.tsdealer.DealerSOAPStub(portAddress, this);
            _stub.setPortName(getdealerSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setdealerSOAPEndpointAddress(java.lang.String address) {
        dealerSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.infoeai.eai.wsServer.tsdealer.Tsdealer_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.infoeai.eai.wsServer.tsdealer.DealerSOAPStub _stub = new com.infoeai.eai.wsServer.tsdealer.DealerSOAPStub(new java.net.URL(dealerSOAP_address), this);
                _stub.setPortName(getdealerSOAPWSDDServiceName());
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
        if ("dealerSOAP".equals(inputPortName)) {
            return getdealerSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://wsServer.eai.infoeai.com/tsdealer/", "tsdealer");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://wsServer.eai.infoeai.com/tsdealer/", "dealerSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("dealerSOAP".equals(portName)) {
            setdealerSOAPEndpointAddress(address);
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
