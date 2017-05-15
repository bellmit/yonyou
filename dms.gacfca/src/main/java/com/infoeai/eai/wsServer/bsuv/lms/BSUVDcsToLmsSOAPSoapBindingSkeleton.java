/**
 * BSUVDcsToLmsSOAPSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.bsuv.lms;

public class BSUVDcsToLmsSOAPSoapBindingSkeleton implements BSUVDcsToLmsSOAP, org.apache.axis.wsdl.Skeleton {
    private BSUVDcsToLmsSOAP impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "from"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "to"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("DCSTOLMS007", _params, new javax.xml.namespace.QName("", "ecCheckFeedBackVO"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.example.org", "ecCheckFeedBackVO"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.example.org", "DCSTOLMS007"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("DCSTOLMS007") == null) {
            _myOperations.put("DCSTOLMS007", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("DCSTOLMS007")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "from"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "to"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("DCSTOLMS008", _params, new javax.xml.namespace.QName("", "trailCustVO"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.example.org", "trailCustVO"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.example.org", "DCSTOLMS008"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("DCSTOLMS008") == null) {
            _myOperations.put("DCSTOLMS008", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("DCSTOLMS008")).add(_oper);
    }

    public BSUVDcsToLmsSOAPSoapBindingSkeleton() {
        this.impl = new BSUVDcsToLmsSOAPSoapBindingImpl();
    }

    public BSUVDcsToLmsSOAPSoapBindingSkeleton(BSUVDcsToLmsSOAP impl) {
        this.impl = impl;
    }
    public EcCheckFeedBackVO[] DCSTOLMS007(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException
    {
        EcCheckFeedBackVO[] ret = impl.DCSTOLMS007(from, to);
        return ret;
    }

    public TrailCustVO[] DCSTOLMS008(java.lang.String from, java.lang.String to) throws java.rmi.RemoteException
    {
        TrailCustVO[] ret = impl.DCSTOLMS008(from, to);
        return ret;
    }

}
