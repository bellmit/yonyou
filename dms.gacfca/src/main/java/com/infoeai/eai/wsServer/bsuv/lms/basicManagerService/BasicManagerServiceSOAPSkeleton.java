/**
 * BasicManagerServiceSOAPSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.bsuv.lms.basicManagerService;

public class BasicManagerServiceSOAPSkeleton implements com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.BasicManagerService_PortType, org.apache.axis.wsdl.Skeleton {
    private com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.BasicManagerService_PortType impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "from"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"), java.util.Date.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "to"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"), java.util.Date.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getMaterialList", _params, new javax.xml.namespace.QName("", "vo"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.example.org/BasicManagerService/", "MaterialVO"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.example.org/BasicManagerService/", "getMaterialList"));
        _oper.setSoapAction("http://www.example.org/BasicManagerService/getMaterialList");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getMaterialList") == null) {
            _myOperations.put("getMaterialList", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getMaterialList")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getDealerList", _params, new javax.xml.namespace.QName("", "vo"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.example.org/BasicManagerService/", "DealerVo"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.example.org/BasicManagerService/", "getDealerList"));
        _oper.setSoapAction("http://www.example.org/BasicManagerService/getDealerList");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getDealerList") == null) {
            _myOperations.put("getDealerList", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getDealerList")).add(_oper);
    }

    public BasicManagerServiceSOAPSkeleton() {
        this.impl = new com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.BasicManagerServiceSOAPImpl();
    }

    public BasicManagerServiceSOAPSkeleton(com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.BasicManagerService_PortType impl) {
        this.impl = impl;
    }
    public com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.MaterialVO[] getMaterialList(java.util.Date from, java.util.Date to) throws java.rmi.RemoteException
    {
        com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.MaterialVO[] ret = impl.getMaterialList(from, to);
        return ret;
    }

    public com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.DealerVo[] getDealerList() throws java.rmi.RemoteException
    {
        com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.DealerVo[] ret = impl.getDealerList();
        return ret;
    }

}
