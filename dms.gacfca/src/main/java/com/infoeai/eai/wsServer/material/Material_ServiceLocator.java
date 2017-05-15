/**
 * Material_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.material;

public class Material_ServiceLocator extends org.apache.axis.client.Service implements com.infoeai.eai.wsServer.material.Material_Service {

    public Material_ServiceLocator() {
    }


    public Material_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Material_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for materialSOAP
    private java.lang.String materialSOAP_address = "http://wsServer.eai.infoeai.com/";

    public java.lang.String getmaterialSOAPAddress() {
        return materialSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String materialSOAPWSDDServiceName = "materialSOAP";

    public java.lang.String getmaterialSOAPWSDDServiceName() {
        return materialSOAPWSDDServiceName;
    }

    public void setmaterialSOAPWSDDServiceName(java.lang.String name) {
        materialSOAPWSDDServiceName = name;
    }

    public com.infoeai.eai.wsServer.material.Material_PortType getmaterialSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(materialSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getmaterialSOAP(endpoint);
    }

    public com.infoeai.eai.wsServer.material.Material_PortType getmaterialSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.infoeai.eai.wsServer.material.MaterialSOAPStub _stub = new com.infoeai.eai.wsServer.material.MaterialSOAPStub(portAddress, this);
            _stub.setPortName(getmaterialSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setmaterialSOAPEndpointAddress(java.lang.String address) {
        materialSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.infoeai.eai.wsServer.material.Material_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.infoeai.eai.wsServer.material.MaterialSOAPStub _stub = new com.infoeai.eai.wsServer.material.MaterialSOAPStub(new java.net.URL(materialSOAP_address), this);
                _stub.setPortName(getmaterialSOAPWSDDServiceName());
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
        if ("materialSOAP".equals(inputPortName)) {
            return getmaterialSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://wsServer.eai.infoeai.com/material/", "material");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://wsServer.eai.infoeai.com/material/", "materialSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("materialSOAP".equals(portName)) {
            setmaterialSOAPEndpointAddress(address);
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
