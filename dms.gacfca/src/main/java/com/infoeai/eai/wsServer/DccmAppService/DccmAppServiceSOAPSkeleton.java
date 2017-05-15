/**
 * DccmAppServiceSOAPSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.DccmAppService;

public class DccmAppServiceSOAPSkeleton implements DccmAppService_PortType, org.apache.axis.wsdl.Skeleton {
    private DccmAppService_PortType impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "From"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "To"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getSalesConsultantList", _params, new javax.xml.namespace.QName("", "salesConsultant"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "SalesConsultantVo"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "GetSalesConsultantList"));
        _oper.setSoapAction("http://www.example.org/DccmAppService/GetSalesConsultantList");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getSalesConsultantList") == null) {
            _myOperations.put("getSalesConsultantList", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getSalesConsultantList")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "from"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "to"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getThePotentialCustomerList", _params, new javax.xml.namespace.QName("", "out"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "PotentialCustomerVo"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "GetThePotentialCustomerList"));
        _oper.setSoapAction("http://www.example.org/DccmAppService/GetThePotentialCustomerList");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getThePotentialCustomerList") == null) {
            _myOperations.put("getThePotentialCustomerList", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getThePotentialCustomerList")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "from"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "to"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getCustomerReceptionList", _params, new javax.xml.namespace.QName("", "out"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "CustomerReceptionVo"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "GetCustomerReceptionList"));
        _oper.setSoapAction("http://www.example.org/DccmAppService/GetCustomerReceptionList");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getCustomerReceptionList") == null) {
            _myOperations.put("getCustomerReceptionList", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getCustomerReceptionList")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "from"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "NewElement"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getCustomerStatusList", _params, new javax.xml.namespace.QName("", "out"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "CustomerStatusVo"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "GetCustomerStatusList"));
        _oper.setSoapAction("http://www.example.org/DccmAppService/GetCustomerStatusList");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getCustomerStatusList") == null) {
            _myOperations.put("getCustomerStatusList", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getCustomerStatusList")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "from"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "to"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getThePotentialCustomerEditList", _params, new javax.xml.namespace.QName("", "out"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "PotentialCustomerVo"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "GetThePotentialCustomerEditList"));
        _oper.setSoapAction("http://www.example.org/DccmAppService/GetThePotentialCustomerEditList");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getThePotentialCustomerEditList") == null) {
            _myOperations.put("getThePotentialCustomerEditList", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getThePotentialCustomerEditList")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "from"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "to"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getCustomerReceptionEditList", _params, new javax.xml.namespace.QName("", "out"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "CustomerReceptionVo"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "GetCustomerReceptionEditList"));
        _oper.setSoapAction("http://www.example.org/DccmAppService/GetCustomerReceptionEditList");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getCustomerReceptionEditList") == null) {
            _myOperations.put("getCustomerReceptionEditList", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getCustomerReceptionEditList")).add(_oper);
    }

    public DccmAppServiceSOAPSkeleton() {
        this.impl = new DccmAppServiceSOAPImpl();
    }

    public DccmAppServiceSOAPSkeleton(DccmAppService_PortType impl) {
        this.impl = impl;
    }
    public SalesConsultantVo[] getSalesConsultantList(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException
    {
        SalesConsultantVo[] ret = impl.getSalesConsultantList(from, to);
        return ret;
    }

    public PotentialCustomerVo[] getThePotentialCustomerList(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException
    {
        PotentialCustomerVo[] ret = impl.getThePotentialCustomerList(from, to);
        return ret;
    }

    public CustomerReceptionVo[] getCustomerReceptionList(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException
    {
        CustomerReceptionVo[] ret = impl.getCustomerReceptionList(from, to);
        return ret;
    }

    public CustomerStatusVo[] getCustomerStatusList(java.lang.String from, java.lang.String newElement) throws java.rmi.RemoteException
    {
        CustomerStatusVo[] ret = impl.getCustomerStatusList(from, newElement);
        return ret;
    }

    public PotentialCustomerVo[] getThePotentialCustomerEditList(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException
    {
        PotentialCustomerVo[] ret = impl.getThePotentialCustomerEditList(from, to);
        return ret;
    }

    public CustomerReceptionVo[] getCustomerReceptionEditList(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException
    {
        CustomerReceptionVo[] ret = impl.getCustomerReceptionEditList(from, to);
        return ret;
    }

}
