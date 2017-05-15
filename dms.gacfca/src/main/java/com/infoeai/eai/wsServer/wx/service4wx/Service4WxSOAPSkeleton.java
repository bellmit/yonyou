/**
 * Service4WxSOAPSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.wx.service4wx;

public class Service4WxSOAPSkeleton implements com.infoeai.eai.wsServer.wx.service4wx.Service4Wx_PortType, org.apache.axis.wsdl.Skeleton {
    private com.infoeai.eai.wsServer.wx.service4wx.Service4Wx_PortType impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.example.org/service4wx/", "customerFeedbackPO"), com.infoeai.eai.wsServer.wx.service4wx.CustomerFeedbackPO[].class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("customerFeedback", _params, new javax.xml.namespace.QName("", "out"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.example.org/service4wx/", "customerFeedback"));
        _oper.setSoapAction("http://www.example.org/service4wx/customerFeedback");
        _myOperationsList.add(_oper);
        if (_myOperations.get("customerFeedback") == null) {
            _myOperations.put("customerFeedback", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("customerFeedback")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.example.org/service4wx/", "customerBindingFeedbackPO"), com.infoeai.eai.wsServer.wx.service4wx.CustomerBindingFeedbackPO[].class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("customerBindingFeedback", _params, new javax.xml.namespace.QName("", "out"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.example.org/service4wx/", "customerBindingFeedback"));
        _oper.setSoapAction("http://www.example.org/service4wx/customerBindingFeedback");
        _myOperationsList.add(_oper);
        if (_myOperations.get("customerBindingFeedback") == null) {
            _myOperations.put("customerBindingFeedback", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("customerBindingFeedback")).add(_oper);
    }

    public Service4WxSOAPSkeleton() {
        this.impl = new com.infoeai.eai.wsServer.wx.service4wx.Service4WxSOAPImpl();
    }

    public Service4WxSOAPSkeleton(com.infoeai.eai.wsServer.wx.service4wx.Service4Wx_PortType impl) {
        this.impl = impl;
    }
    public java.lang.String customerFeedback(com.infoeai.eai.wsServer.wx.service4wx.CustomerFeedbackPO[] in0) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.customerFeedback(in0);
        return ret;
    }

    public java.lang.String customerBindingFeedback(com.infoeai.eai.wsServer.wx.service4wx.CustomerBindingFeedbackPO[] in1) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.customerBindingFeedback(in1);
        return ret;
    }

}
