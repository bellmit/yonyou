/**
 * DccmAppService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.DccmAppService;

public class DccmAppService_ServiceLocator extends org.apache.axis.client.Service implements DccmAppService_Service {

    public DccmAppService_ServiceLocator() {
    }


    public DccmAppService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DccmAppService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DccmAppServiceSOAP
    private java.lang.String DccmAppServiceSOAP_address = "http://localhost:8080/DMS_HMCI/services/DccmAppServiceSOAP";

    public java.lang.String getDccmAppServiceSOAPAddress() {
        return DccmAppServiceSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DccmAppServiceSOAPWSDDServiceName = "DccmAppServiceSOAP";

    public java.lang.String getDccmAppServiceSOAPWSDDServiceName() {
        return DccmAppServiceSOAPWSDDServiceName;
    }

    public void setDccmAppServiceSOAPWSDDServiceName(java.lang.String name) {
        DccmAppServiceSOAPWSDDServiceName = name;
    }

    public DccmAppService_PortType getDccmAppServiceSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DccmAppServiceSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDccmAppServiceSOAP(endpoint);
    }

    public DccmAppService_PortType getDccmAppServiceSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            DccmAppServiceSOAPStub _stub = new DccmAppServiceSOAPStub(portAddress, this);
            _stub.setPortName(getDccmAppServiceSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDccmAppServiceSOAPEndpointAddress(java.lang.String address) {
        DccmAppServiceSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (DccmAppService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                DccmAppServiceSOAPStub _stub = new DccmAppServiceSOAPStub(new java.net.URL(DccmAppServiceSOAP_address), this);
                _stub.setPortName(getDccmAppServiceSOAPWSDDServiceName());
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
        if ("DccmAppServiceSOAP".equals(inputPortName)) {
            return getDccmAppServiceSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "DccmAppService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "DccmAppServiceSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DccmAppServiceSOAP".equals(portName)) {
            setDccmAppServiceSOAPEndpointAddress(address);
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
