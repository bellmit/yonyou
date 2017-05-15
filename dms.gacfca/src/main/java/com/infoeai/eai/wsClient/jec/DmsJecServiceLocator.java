/**
 * DmsJecServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.jec;

public class DmsJecServiceLocator extends org.apache.axis.client.Service implements com.infoeai.eai.wsClient.jec.DmsJecService {

    public DmsJecServiceLocator() {
    }


    public DmsJecServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DmsJecServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DmsJecServiceHttpPort
    private java.lang.String DmsJecServiceHttpPort_address = "http://bma.boldseas.com/bws/services/DmsJecService";

    public java.lang.String getDmsJecServiceHttpPortAddress() {
        return DmsJecServiceHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DmsJecServiceHttpPortWSDDServiceName = "DmsJecServiceHttpPort";

    public java.lang.String getDmsJecServiceHttpPortWSDDServiceName() {
        return DmsJecServiceHttpPortWSDDServiceName;
    }

    public void setDmsJecServiceHttpPortWSDDServiceName(java.lang.String name) {
        DmsJecServiceHttpPortWSDDServiceName = name;
    }

    public com.infoeai.eai.wsClient.jec.DmsJecServicePortType getDmsJecServiceHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DmsJecServiceHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDmsJecServiceHttpPort(endpoint);
    }

    public com.infoeai.eai.wsClient.jec.DmsJecServicePortType getDmsJecServiceHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.infoeai.eai.wsClient.jec.DmsJecServiceHttpBindingStub _stub = new com.infoeai.eai.wsClient.jec.DmsJecServiceHttpBindingStub(portAddress, this);
            _stub.setPortName(getDmsJecServiceHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDmsJecServiceHttpPortEndpointAddress(java.lang.String address) {
        DmsJecServiceHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.infoeai.eai.wsClient.jec.DmsJecServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.infoeai.eai.wsClient.jec.DmsJecServiceHttpBindingStub _stub = new com.infoeai.eai.wsClient.jec.DmsJecServiceHttpBindingStub(new java.net.URL(DmsJecServiceHttpPort_address), this);
                _stub.setPortName(getDmsJecServiceHttpPortWSDDServiceName());
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
        if ("DmsJecServiceHttpPort".equals(inputPortName)) {
            return getDmsJecServiceHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://boldseas.com/dmsjecservice", "DmsJecService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://boldseas.com/dmsjecservice", "DmsJecServiceHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DmsJecServiceHttpPort".equals(portName)) {
            setDmsJecServiceHttpPortEndpointAddress(address);
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
