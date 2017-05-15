/**
 * DMSWebserviceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.lms.dmswebservice;

public class DMSWebserviceLocator extends org.apache.axis.client.Service implements DMSWebservice {

    public DMSWebserviceLocator() {
    }


    public DMSWebserviceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DMSWebserviceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DMSWebserviceSoap
    private java.lang.String DMSWebserviceSoap_address = "http://testdmsws.jeepsupport.com.cn/DMSWebservice.asmx";

    public java.lang.String getDMSWebserviceSoapAddress() {
        return DMSWebserviceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DMSWebserviceSoapWSDDServiceName = "DMSWebserviceSoap";

    public java.lang.String getDMSWebserviceSoapWSDDServiceName() {
        return DMSWebserviceSoapWSDDServiceName;
    }

    public void setDMSWebserviceSoapWSDDServiceName(java.lang.String name) {
        DMSWebserviceSoapWSDDServiceName = name;
    }

    public DMSWebserviceSoap getDMSWebserviceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DMSWebserviceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDMSWebserviceSoap(endpoint);
    }

    public DMSWebserviceSoap getDMSWebserviceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            DMSWebserviceSoapStub _stub = new DMSWebserviceSoapStub(portAddress, this);
            _stub.setPortName(getDMSWebserviceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDMSWebserviceSoapEndpointAddress(java.lang.String address) {
        DMSWebserviceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (DMSWebserviceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                DMSWebserviceSoapStub _stub = new DMSWebserviceSoapStub(new java.net.URL(DMSWebserviceSoap_address), this);
                _stub.setPortName(getDMSWebserviceSoapWSDDServiceName());
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
        if ("DMSWebserviceSoap".equals(inputPortName)) {
            return getDMSWebserviceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "DMSWebservice");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "DMSWebserviceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DMSWebserviceSoap".equals(portName)) {
            setDMSWebserviceSoapEndpointAddress(address);
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
