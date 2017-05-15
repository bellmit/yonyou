/**
 * DealerSOAPSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.tsdealer;

public class DealerSOAPSkeleton implements com.infoeai.eai.wsServer.tsdealer.Tsdealer_PortType, org.apache.axis.wsdl.Skeleton {
    private com.infoeai.eai.wsServer.tsdealer.Tsdealer_PortType impl;
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
        _oper = new org.apache.axis.description.OperationDesc("dealerInfo", _params, new javax.xml.namespace.QName("", "dealerList"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://wsServer.eai.infoeai.com/tsdealer/", "tsDealerVO"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://wsServer.eai.infoeai.com/tsdealer/", "dealerInfo"));
        _oper.setSoapAction("http://wsServer.eai.infoeai.com/tsdealer/NewOperation");
        _myOperationsList.add(_oper);
        if (_myOperations.get("dealerInfo") == null) {
            _myOperations.put("dealerInfo", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("dealerInfo")).add(_oper);
    }

    public DealerSOAPSkeleton() {
        this.impl = new com.infoeai.eai.wsServer.tsdealer.DealerSOAPImpl();
    }

    public DealerSOAPSkeleton(com.infoeai.eai.wsServer.tsdealer.Tsdealer_PortType impl) {
        this.impl = impl;
    }
    public com.infoeai.eai.wsServer.tsdealer.TsDealerVO[] dealerInfo(java.util.Date startDate) throws java.rmi.RemoteException
    {
        com.infoeai.eai.wsServer.tsdealer.TsDealerVO[] ret = impl.dealerInfo(startDate);
        return ret;
    }

}
