/**
 * K4SapToDmsSOAPSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.k4SapToDms;

public class K4SapToDmsSOAPSkeleton implements com.infoeai.eai.wsServer.k4SapToDms.K4SapToDms_PortType, org.apache.axis.wsdl.Skeleton {
    private com.infoeai.eai.wsServer.k4SapToDms.K4SapToDms_PortType impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sapData"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("sendDataToDMS", _params, new javax.xml.namespace.QName("", "dmsMessage"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.example.org/K4SapToDms/", "sendDataToDMS"));
        _oper.setSoapAction("http://www.example.org/K4SapToDms/sendDataToDMS");
        _myOperationsList.add(_oper);
        if (_myOperations.get("sendDataToDMS") == null) {
            _myOperations.put("sendDataToDMS", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("sendDataToDMS")).add(_oper);
    }

    public K4SapToDmsSOAPSkeleton() {
        this.impl = new com.infoeai.eai.wsServer.k4SapToDms.K4SapToDmsSOAPImpl();
    }

    public K4SapToDmsSOAPSkeleton(com.infoeai.eai.wsServer.k4SapToDms.K4SapToDms_PortType impl) {
        this.impl = impl;
    }
    public java.lang.String sendDataToDMS(java.lang.String sapData) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.sendDataToDMS(sapData);
        return ret;
    }

}
