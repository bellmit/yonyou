/**
 * OrderServicePortSoap11Stub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.bsuv.lms;

public class OrderServicePortSoap11Stub extends org.apache.axis.client.Stub implements OrderServicePort {
	private java.util.Vector cachedSerClasses = new java.util.Vector();
	private java.util.Vector cachedSerQNames = new java.util.Vector();
	private java.util.Vector cachedSerFactories = new java.util.Vector();
	private java.util.Vector cachedDeserFactories = new java.util.Vector();

	static org.apache.axis.description.OperationDesc[] _operations;

	static {
		_operations = new org.apache.axis.description.OperationDesc[4];
		_initOperationDesc1();
	}

	private static void _initOperationDesc1() {
		org.apache.axis.description.OperationDesc oper;
		org.apache.axis.description.ParameterDesc param;
		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("GetTypeBTimeOut");
		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "GetTypeBTimeOutRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">GetTypeBTimeOutRequest"), GetTypeBTimeOutRequest.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">GetTypeBTimeOutResponse"));
		oper.setReturnClass(Customer[].class);
		oper.setReturnQName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "GetTypeBTimeOutResponse"));
		param = oper.getReturnParamDesc();
		param.setItemQName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "Customers"));
		oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[0] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("DepositVerification");
		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "DepositVerificationRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">DepositVerificationRequest"), DepositVerificationRequest.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">DepositVerificationResponse"));
		oper.setReturnClass(DepositVerificationResponse.class);
		oper.setReturnQName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "DepositVerificationResponse"));
		oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[1] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("GetOrders");
		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "GetOrdersRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">GetOrdersRequest"), GetOrdersRequest.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">GetOrdersResponse"));
		oper.setReturnClass(Order[].class);
		oper.setReturnQName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "GetOrdersResponse"));
		param = oper.getReturnParamDesc();
		param.setItemQName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "Orders"));
		oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[2] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("GetSaleLeads");
		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "GetSaleLeadsRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">GetSaleLeadsRequest"), GetSaleLeadsRequest.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">GetSaleLeadsResponse"));
		oper.setReturnClass(SaleLead[].class);
		oper.setReturnQName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "GetSaleLeadsResponse"));
		param = oper.getReturnParamDesc();
		param.setItemQName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "saleLeads"));
		oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[3] = oper;

	}

	public OrderServicePortSoap11Stub() throws org.apache.axis.AxisFault {
		this(null);
	}

	public OrderServicePortSoap11Stub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		this(service);
		super.cachedEndpoint = endpointURL;
	}

	public OrderServicePortSoap11Stub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		if (service == null) {
			super.service = new org.apache.axis.client.Service();
		} else {
			super.service = service;
		}
		((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.2");
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
		qName = new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">DepositVerificationRequest");
		cachedSerQNames.add(qName);
		cls = DepositVerificationRequest.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">DepositVerificationResponse");
		cachedSerQNames.add(qName);
		cls = DepositVerificationResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">GetOrdersRequest");
		cachedSerQNames.add(qName);
		cls = GetOrdersRequest.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">GetOrdersResponse");
		cachedSerQNames.add(qName);
		cls = Order[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName("http://models.generate.boldseas.com", "Order");
		qName2 = new javax.xml.namespace.QName("http://models.generate.boldseas.com", "Orders");
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">GetSaleLeadsRequest");
		cachedSerQNames.add(qName);
		cls = GetSaleLeadsRequest.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">GetSaleLeadsResponse");
		cachedSerQNames.add(qName);
		cls = SaleLead[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName("http://models.generate.boldseas.com", "SaleLead");
		qName2 = new javax.xml.namespace.QName("http://models.generate.boldseas.com", "saleLeads");
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">GetTypeBTimeOutRequest");
		cachedSerQNames.add(qName);
		cls = GetTypeBTimeOutRequest.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">GetTypeBTimeOutResponse");
		cachedSerQNames.add(qName);
		cls = Customer[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName("http://models.generate.boldseas.com", "Customer");
		qName2 = new javax.xml.namespace.QName("http://models.generate.boldseas.com", "Customers");
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName("http://models.generate.boldseas.com", "Customer");
		cachedSerQNames.add(qName);
		cls = Customer.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://models.generate.boldseas.com", "Order");
		cachedSerQNames.add(qName);
		cls = Order.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://models.generate.boldseas.com", "SaleLead");
		cachedSerQNames.add(qName);
		cls = SaleLead.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

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
						javax.xml.namespace.QName qName = (javax.xml.namespace.QName) cachedSerQNames.get(i);
						java.lang.Object x = cachedSerFactories.get(i);
						if (x instanceof Class) {
							java.lang.Class sf = (java.lang.Class) cachedSerFactories.get(i);
							java.lang.Class df = (java.lang.Class) cachedDeserFactories.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						} else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
							org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory) cachedSerFactories.get(i);
							org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory) cachedDeserFactories.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						}
					}
				}
			}
			return _call;
		} catch (java.lang.Throwable _t) {
			throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
		}
	}

	public Customer[] getTypeBTimeOut(GetTypeBTimeOutRequest getTypeBTimeOutRequest) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("", "GetTypeBTimeOut"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { getTypeBTimeOutRequest });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Customer[]) _resp;
				} catch (java.lang.Exception _exception) {
					return (Customer[]) org.apache.axis.utils.JavaUtils.convert(_resp, Customer[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public DepositVerificationResponse depositVerification(DepositVerificationRequest depositVerificationRequest) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[1]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("", "DepositVerification"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { depositVerificationRequest });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (DepositVerificationResponse) _resp;
				} catch (java.lang.Exception _exception) {
					return (DepositVerificationResponse) org.apache.axis.utils.JavaUtils.convert(_resp, DepositVerificationResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Order[] getOrders(GetOrdersRequest getOrdersRequest) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[2]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("", "GetOrders"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { getOrdersRequest });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Order[]) _resp;
				} catch (java.lang.Exception _exception) {
					return (Order[]) org.apache.axis.utils.JavaUtils.convert(_resp, Order[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public SaleLead[] getSaleLeads(GetSaleLeadsRequest getSaleLeadsRequest) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[3]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("", "GetSaleLeads"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { getSaleLeadsRequest });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (SaleLead[]) _resp;
				} catch (java.lang.Exception _exception) {
					return (SaleLead[]) org.apache.axis.utils.JavaUtils.convert(_resp, SaleLead[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

}
