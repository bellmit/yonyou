/**
 * MaterialSOAPSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.material;

public class MaterialSOAPSkeleton implements com.infoeai.eai.wsServer.material.Material_PortType, org.apache.axis.wsdl.Skeleton {
    private com.infoeai.eai.wsServer.material.Material_PortType impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "startDate"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"), java.util.Date.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("materialInfo", _params, new javax.xml.namespace.QName("", "materialList"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://wsServer.eai.infoeai.com/material/", "tsMaterialVO"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://wsServer.eai.infoeai.com/material/", "materialInfo"));
        _oper.setSoapAction("http://wsServer.eai.infoeai.com/material/NewOperation");
        _myOperationsList.add(_oper);
        if (_myOperations.get("materialInfo") == null) {
            _myOperations.put("materialInfo", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("materialInfo")).add(_oper);
    }

    public MaterialSOAPSkeleton() {
        this.impl = new com.infoeai.eai.wsServer.material.MaterialSOAPImpl();
    }

    public MaterialSOAPSkeleton(com.infoeai.eai.wsServer.material.Material_PortType impl) {
        this.impl = impl;
    }
    public com.infoeai.eai.wsServer.material.TsMaterialVO[] materialInfo(java.util.Date startDate) throws java.rmi.RemoteException
    {
        com.infoeai.eai.wsServer.material.TsMaterialVO[] ret = impl.materialInfo(startDate);
        return ret;
    }

}
