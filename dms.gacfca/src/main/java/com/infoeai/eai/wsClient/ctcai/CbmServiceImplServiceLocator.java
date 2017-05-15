/**
 * CbmServiceImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.ctcai;

public class CbmServiceImplServiceLocator extends org.apache.axis.client.Service implements com.infoeai.eai.wsClient.ctcai.CbmServiceImplService {

    public CbmServiceImplServiceLocator() {
    }


    public CbmServiceImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CbmServiceImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CbmServiceImplPort
    private java.lang.String CbmServiceImplPort_address = "http://111.207.214.6:8888/cbm/services/cbm";

    public java.lang.String getCbmServiceImplPortAddress() {
        return CbmServiceImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CbmServiceImplPortWSDDServiceName = "CbmServiceImplPort";

    public java.lang.String getCbmServiceImplPortWSDDServiceName() {
        return CbmServiceImplPortWSDDServiceName;
    }

    public void setCbmServiceImplPortWSDDServiceName(java.lang.String name) {
        CbmServiceImplPortWSDDServiceName = name;
    }

    public com.infoeai.eai.wsClient.ctcai.CbmService getCbmServiceImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CbmServiceImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCbmServiceImplPort(endpoint);
    }

    public com.infoeai.eai.wsClient.ctcai.CbmService getCbmServiceImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.infoeai.eai.wsClient.ctcai.CbmServiceImplServiceSoapBindingStub _stub = new com.infoeai.eai.wsClient.ctcai.CbmServiceImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCbmServiceImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCbmServiceImplPortEndpointAddress(java.lang.String address) {
        CbmServiceImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.infoeai.eai.wsClient.ctcai.CbmService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.infoeai.eai.wsClient.ctcai.CbmServiceImplServiceSoapBindingStub _stub = new com.infoeai.eai.wsClient.ctcai.CbmServiceImplServiceSoapBindingStub(new java.net.URL(CbmServiceImplPort_address), this);
                _stub.setPortName(getCbmServiceImplPortWSDDServiceName());
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
        if ("CbmServiceImplPort".equals(inputPortName)) {
            return getCbmServiceImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webService.cbm.norteksoft.com/", "CbmServiceImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webService.cbm.norteksoft.com/", "CbmServiceImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CbmServiceImplPort".equals(portName)) {
            setCbmServiceImplPortEndpointAddress(address);
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
