/**
 * LmsServiceSOAPSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.SI12;

public class LmsServiceSOAPSkeleton implements com.infoeai.eai.wsServer.SI12.LmsService_PortType, org.apache.axis.wsdl.Skeleton {
    private com.infoeai.eai.wsServer.SI12.LmsService_PortType impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.example.org/lmsService/", "infoPO"), com.infoeai.eai.wsServer.SI12.InfoPO[].class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getSalesCustInfo", _params, new javax.xml.namespace.QName("", "out"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.example.org/lmsService/", "getSalesCustInfo"));
        _oper.setSoapAction("http://www.example.org/lmsService/getSalesCustInfo");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getSalesCustInfo") == null) {
            _myOperations.put("getSalesCustInfo", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getSalesCustInfo")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.example.org/lmsService/", "createInfoPO"), com.infoeai.eai.wsServer.SI12.CreateInfoPO[].class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getCreateSalesCustInfo", _params, new javax.xml.namespace.QName("", "out"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.example.org/lmsService/", "getCreateSalesCustInfo"));
        _oper.setSoapAction("http://www.example.org/lmsService/getCreateSalesCustInfo");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getCreateSalesCustInfo") == null) {
            _myOperations.put("getCreateSalesCustInfo", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getCreateSalesCustInfo")).add(_oper);
    }

    public LmsServiceSOAPSkeleton() {
        this.impl = new com.infoeai.eai.wsServer.SI12.LmsServiceSOAPImpl();
    }

    public LmsServiceSOAPSkeleton(com.infoeai.eai.wsServer.SI12.LmsService_PortType impl) {
        this.impl = impl;
    }
    public java.lang.String getSalesCustInfo(com.infoeai.eai.wsServer.SI12.InfoPO[] in0) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getSalesCustInfo(in0);
        return ret;
    }

    public java.lang.String getCreateSalesCustInfo(com.infoeai.eai.wsServer.SI12.CreateInfoPO[] in) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getCreateSalesCustInfo(in);
        return ret;
    }

}
