/**
 * SapDcsServiceSOAPSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.SapDcsService;

public class SapDcsServiceSOAPSkeleton implements com.infoeai.eai.wsServer.SapDcsService.SapDcsService_PortType, org.apache.axis.wsdl.Skeleton {
    private com.infoeai.eai.wsServer.SapDcsService.SapDcsService_PortType impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dcs.chrysler.com.cn/SapDcsService/", "sapOutboundVO"), com.infoeai.eai.wsServer.SapDcsService.SapOutboundVO[].class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("execute", _params, new javax.xml.namespace.QName("", "out"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://dcs.chrysler.com.cn/SapDcsService/", "execute"));
        _oper.setSoapAction("http://dcs.chrysler.com.cn/SapDcsService/execute");
        _myOperationsList.add(_oper);
        if (_myOperations.get("execute") == null) {
            _myOperations.put("execute", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("execute")).add(_oper);
    }

    public SapDcsServiceSOAPSkeleton() {
        this.impl = new com.infoeai.eai.wsServer.SapDcsService.SapDcsServiceSOAPImpl();
    }

    public SapDcsServiceSOAPSkeleton(com.infoeai.eai.wsServer.SapDcsService.SapDcsService_PortType impl) {
        this.impl = impl;
    }
    public java.lang.String execute(com.infoeai.eai.wsServer.SapDcsService.SapOutboundVO[] in0) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.execute(in0);
        return ret;
    }

}
