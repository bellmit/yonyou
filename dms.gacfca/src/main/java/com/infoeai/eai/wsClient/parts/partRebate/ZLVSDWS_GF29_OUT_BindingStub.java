/**
 * ZLVSDWS_GF29_OUT_BindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.partRebate;

import java.io.IOException;
import java.util.Properties;

public class ZLVSDWS_GF29_OUT_BindingStub extends org.apache.axis.client.Stub implements com.infoeai.eai.wsClient.parts.partRebate.ZLVSDWS_GF29_OUT_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[1];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ZlvsdwsGf29Out");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "IAudat"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "TpmyRDate"), com.infoeai.eai.wsClient.parts.partRebate.TpmsRDate[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "IBelnr"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char10"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "IFldat"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZtpmyRDate"), com.infoeai.eai.wsClient.parts.partRebate.ZtpmsRFldat[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "IType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char2"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "IVatno"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char8"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "IWerks"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char4"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "IZzcliente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char7"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TbOutput"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "TableOfZlvsdwsGf29Out"), com.infoeai.eai.wsClient.parts.partRebate.ZlvsdwsGf29Out[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TbReturn"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "TableOfBapiret1"), com.infoeai.eai.wsClient.parts.partRebate.Bapiret1[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Atp"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "curr15.2"), java.math.BigDecimal.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Blocked"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "curr15.2"), java.math.BigDecimal.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Cum"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "curr15.2"), java.math.BigDecimal.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Rebate"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "curr15.2"), java.math.BigDecimal.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Saldv"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "curr15.2"), java.math.BigDecimal.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Sumin"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "curr15.2"), java.math.BigDecimal.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Sumout"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "curr15.2"), java.math.BigDecimal.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Used"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "curr15.2"), java.math.BigDecimal.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

    }

    public ZLVSDWS_GF29_OUT_BindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ZLVSDWS_GF29_OUT_BindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ZLVSDWS_GF29_OUT_BindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char1");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char10");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char100");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char18");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char2");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char20");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char220");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char30");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char4");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char50");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char600");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char7");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "char8");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "curr13.2");
            cachedSerQNames.add(qName);
            cls = java.math.BigDecimal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "curr15.2");
            cachedSerQNames.add(qName);
            cls = java.math.BigDecimal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "date");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "decimal15.2");
            cachedSerQNames.add(qName);
            cls = java.math.BigDecimal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "numeric3");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "numeric6");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "quantum15.0");
            cachedSerQNames.add(qName);
            cls = java.math.BigDecimal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "time");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "Bapiret1");
            cachedSerQNames.add(qName);
            cls = com.infoeai.eai.wsClient.parts.partRebate.Bapiret1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "TableOfBapiret1");
            cachedSerQNames.add(qName);
            cls = com.infoeai.eai.wsClient.parts.partRebate.Bapiret1[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "Bapiret1");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "TableOfZlvsdwsGf29Out");
            cachedSerQNames.add(qName);
            cls = com.infoeai.eai.wsClient.parts.partRebate.ZlvsdwsGf29Out[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZlvsdwsGf29Out");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "TpmsRDate");
            cachedSerQNames.add(qName);
            cls = com.infoeai.eai.wsClient.parts.partRebate.TpmsRDate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "TpmyRDate");
            cachedSerQNames.add(qName);
            cls = com.infoeai.eai.wsClient.parts.partRebate.TpmsRDate[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "TpmsRDate");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZlvsdwsGf29Out");
            cachedSerQNames.add(qName);
            cls = com.infoeai.eai.wsClient.parts.partRebate.ZlvsdwsGf29Out.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZtpmsRFldat");
            cachedSerQNames.add(qName);
            cls = com.infoeai.eai.wsClient.parts.partRebate.ZtpmsRFldat.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZtpmyRDate");
            cachedSerQNames.add(qName);
            cls = com.infoeai.eai.wsClient.parts.partRebate.ZtpmsRFldat[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZtpmsRFldat");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public void zlvsdwsGf29Out(com.infoeai.eai.wsClient.parts.partRebate.TpmsRDate[] IAudat, java.lang.String IBelnr, com.infoeai.eai.wsClient.parts.partRebate.ZtpmsRFldat[] IFldat, java.lang.String IType, java.lang.String IVatno, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.partRebate.holders.TableOfZlvsdwsGf29OutHolder tbOutput, com.infoeai.eai.wsClient.parts.partRebate.holders.TableOfBapiret1Holder tbReturn, javax.xml.rpc.holders.BigDecimalHolder atp, javax.xml.rpc.holders.BigDecimalHolder blocked, javax.xml.rpc.holders.BigDecimalHolder cum, javax.xml.rpc.holders.BigDecimalHolder rebate, javax.xml.rpc.holders.BigDecimalHolder saldv, javax.xml.rpc.holders.BigDecimalHolder sumin, javax.xml.rpc.holders.BigDecimalHolder sumout, javax.xml.rpc.holders.BigDecimalHolder used) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZlvsdwsGf29Out"));
		// 连接用 用户名/密码取得
        Properties props = new Properties();
		String strUserName = "";
		String strPassword = "";
		try {
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("sapuserinfo.properties"));
			strUserName = props.getProperty("username");
			strPassword = props.getProperty("password");
		} catch (IOException e) {
			e.getMessage();
		}
        _call.setUsername(strUserName);//用户名
        _call.setPassword(strPassword);//密码
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {IAudat, IBelnr, IFldat, IType, IVatno, IWerks, IZzcliente, tbOutput.value, tbReturn.value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                tbOutput.value = (com.infoeai.eai.wsClient.parts.partRebate.ZlvsdwsGf29Out[]) _output.get(new javax.xml.namespace.QName("", "TbOutput"));
            } catch (java.lang.Exception _exception) {
                tbOutput.value = (com.infoeai.eai.wsClient.parts.partRebate.ZlvsdwsGf29Out[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "TbOutput")), com.infoeai.eai.wsClient.parts.partRebate.ZlvsdwsGf29Out[].class);
            }
            try {
                tbReturn.value = (com.infoeai.eai.wsClient.parts.partRebate.Bapiret1[]) _output.get(new javax.xml.namespace.QName("", "TbReturn"));
            } catch (java.lang.Exception _exception) {
                tbReturn.value = (com.infoeai.eai.wsClient.parts.partRebate.Bapiret1[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "TbReturn")), com.infoeai.eai.wsClient.parts.partRebate.Bapiret1[].class);
            }
            try {
                atp.value = (java.math.BigDecimal) _output.get(new javax.xml.namespace.QName("", "Atp"));
            } catch (java.lang.Exception _exception) {
                atp.value = (java.math.BigDecimal) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "Atp")), java.math.BigDecimal.class);
            }
            try {
                blocked.value = (java.math.BigDecimal) _output.get(new javax.xml.namespace.QName("", "Blocked"));
            } catch (java.lang.Exception _exception) {
                blocked.value = (java.math.BigDecimal) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "Blocked")), java.math.BigDecimal.class);
            }
            try {
                cum.value = (java.math.BigDecimal) _output.get(new javax.xml.namespace.QName("", "Cum"));
            } catch (java.lang.Exception _exception) {
                cum.value = (java.math.BigDecimal) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "Cum")), java.math.BigDecimal.class);
            }
            try {
                rebate.value = (java.math.BigDecimal) _output.get(new javax.xml.namespace.QName("", "Rebate"));
            } catch (java.lang.Exception _exception) {
                rebate.value = (java.math.BigDecimal) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "Rebate")), java.math.BigDecimal.class);
            }
            try {
                saldv.value = (java.math.BigDecimal) _output.get(new javax.xml.namespace.QName("", "Saldv"));
            } catch (java.lang.Exception _exception) {
                saldv.value = (java.math.BigDecimal) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "Saldv")), java.math.BigDecimal.class);
            }
            try {
                sumin.value = (java.math.BigDecimal) _output.get(new javax.xml.namespace.QName("", "Sumin"));
            } catch (java.lang.Exception _exception) {
                sumin.value = (java.math.BigDecimal) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "Sumin")), java.math.BigDecimal.class);
            }
            try {
                sumout.value = (java.math.BigDecimal) _output.get(new javax.xml.namespace.QName("", "Sumout"));
            } catch (java.lang.Exception _exception) {
                sumout.value = (java.math.BigDecimal) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "Sumout")), java.math.BigDecimal.class);
            }
            try {
                used.value = (java.math.BigDecimal) _output.get(new javax.xml.namespace.QName("", "Used"));
            } catch (java.lang.Exception _exception) {
                used.value = (java.math.BigDecimal) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "Used")), java.math.BigDecimal.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
