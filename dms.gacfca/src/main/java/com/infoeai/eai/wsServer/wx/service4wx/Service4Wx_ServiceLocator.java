/**
 * Service4Wx_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.wx.service4wx;

public class Service4Wx_ServiceLocator extends org.apache.axis.client.Service implements com.infoeai.eai.wsServer.wx.service4wx.Service4Wx_Service {

    public Service4Wx_ServiceLocator() {
    }


    public Service4Wx_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Service4Wx_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for service4wxSOAP
    private java.lang.String service4wxSOAP_address = "http://58.240.171.103:9080/CGCSLDMS/services/service4wxSOAP";

    public java.lang.String getservice4wxSOAPAddress() {
        return service4wxSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String service4wxSOAPWSDDServiceName = "service4wxSOAP";

    public java.lang.String getservice4wxSOAPWSDDServiceName() {
        return service4wxSOAPWSDDServiceName;
    }

    public void setservice4wxSOAPWSDDServiceName(java.lang.String name) {
        service4wxSOAPWSDDServiceName = name;
    }

    public com.infoeai.eai.wsServer.wx.service4wx.Service4Wx_PortType getservice4wxSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(service4wxSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getservice4wxSOAP(endpoint);
    }

    public com.infoeai.eai.wsServer.wx.service4wx.Service4Wx_PortType getservice4wxSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.infoeai.eai.wsServer.wx.service4wx.Service4WxSOAPStub _stub = new com.infoeai.eai.wsServer.wx.service4wx.Service4WxSOAPStub(portAddress, this);
            _stub.setPortName(getservice4wxSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setservice4wxSOAPEndpointAddress(java.lang.String address) {
        service4wxSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.infoeai.eai.wsServer.wx.service4wx.Service4Wx_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.infoeai.eai.wsServer.wx.service4wx.Service4WxSOAPStub _stub = new com.infoeai.eai.wsServer.wx.service4wx.Service4WxSOAPStub(new java.net.URL(service4wxSOAP_address), this);
                _stub.setPortName(getservice4wxSOAPWSDDServiceName());
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
        if ("service4wxSOAP".equals(inputPortName)) {
            return getservice4wxSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.example.org/service4wx/", "service4wx");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.example.org/service4wx/", "service4wxSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("service4wxSOAP".equals(portName)) {
            setservice4wxSOAPEndpointAddress(address);
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
