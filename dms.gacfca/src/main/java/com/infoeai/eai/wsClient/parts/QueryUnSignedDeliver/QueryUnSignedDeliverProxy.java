package com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver;

public class QueryUnSignedDeliverProxy implements com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver.QueryUnSignedDeliver_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver.QueryUnSignedDeliver_PortType queryUnSignedDeliver_PortType = null;
  
  public QueryUnSignedDeliverProxy() {
    _initQueryUnSignedDeliverProxy();
  }
  
  public QueryUnSignedDeliverProxy(String endpoint) {
    _endpoint = endpoint;
    _initQueryUnSignedDeliverProxy();
  }
  
  private void _initQueryUnSignedDeliverProxy() {
    try {
      queryUnSignedDeliver_PortType = (new com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver.QueryUnSignedDeliver_ServiceLocator()).getQueryUnSignedDeliverSOAP();
      if (queryUnSignedDeliver_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)queryUnSignedDeliver_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)queryUnSignedDeliver_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (queryUnSignedDeliver_PortType != null)
      ((javax.xml.rpc.Stub)queryUnSignedDeliver_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver.QueryUnSignedDeliver_PortType getQueryUnSignedDeliver_PortType() {
    if (queryUnSignedDeliver_PortType == null)
      _initQueryUnSignedDeliverProxy();
    return queryUnSignedDeliver_PortType;
  }
  
  public com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver.MmQueryUnSignedDeliverSAPVO[] mmQueryUnSignedDeliver(java.lang.String entityCode, java.lang.String deliveryOrderNo, java.lang.String partNo, java.lang.String transNo) throws java.rmi.RemoteException{
    if (queryUnSignedDeliver_PortType == null)
      _initQueryUnSignedDeliverProxy();
    return queryUnSignedDeliver_PortType.mmQueryUnSignedDeliver(entityCode, deliveryOrderNo, partNo, transNo);
  }
  
  
}