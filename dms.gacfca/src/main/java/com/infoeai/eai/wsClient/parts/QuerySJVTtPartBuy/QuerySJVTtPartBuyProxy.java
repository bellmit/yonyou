package com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy;

public class QuerySJVTtPartBuyProxy implements com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy.QuerySJVTtPartBuy_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy.QuerySJVTtPartBuy_PortType querySJVTtPartBuy_PortType = null;
  
  public QuerySJVTtPartBuyProxy() {
    _initQuerySJVTtPartBuyProxy();
  }
  
  public QuerySJVTtPartBuyProxy(String endpoint) {
    _endpoint = endpoint;
    _initQuerySJVTtPartBuyProxy();
  }
  
  private void _initQuerySJVTtPartBuyProxy() {
    try {
      querySJVTtPartBuy_PortType = (new com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy.QuerySJVTtPartBuy_ServiceLocator()).getQuerySJVTtPartBuySOAP();
      if (querySJVTtPartBuy_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)querySJVTtPartBuy_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)querySJVTtPartBuy_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (querySJVTtPartBuy_PortType != null)
      ((javax.xml.rpc.Stub)querySJVTtPartBuy_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy.QuerySJVTtPartBuy_PortType getQuerySJVTtPartBuy_PortType() {
    if (querySJVTtPartBuy_PortType == null)
      _initQuerySJVTtPartBuyProxy();
    return querySJVTtPartBuy_PortType;
  }
  
  public com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy.MmQuerySJVTtPartBuyPO[] mmQuerySJVTtPartBuy(java.lang.String entityCode, java.lang.String deliveryOrderNo, java.lang.String transNo, java.lang.String sapOrderNo, java.lang.String partNo, java.lang.String partName) throws java.rmi.RemoteException{
    if (querySJVTtPartBuy_PortType == null)
      _initQuerySJVTtPartBuyProxy();
    return querySJVTtPartBuy_PortType.mmQuerySJVTtPartBuy(entityCode, deliveryOrderNo, transNo, sapOrderNo, partNo, partName);
  }
  
  
}