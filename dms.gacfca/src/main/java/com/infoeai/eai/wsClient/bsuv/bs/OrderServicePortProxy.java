package com.infoeai.eai.wsClient.bsuv.bs;

public class OrderServicePortProxy implements OrderServicePort {
  private String _endpoint = null;
  private OrderServicePort orderServicePort = null;
  
  public OrderServicePortProxy() {
    _initOrderServicePortProxy();
  }
  
  public OrderServicePortProxy(String endpoint) {
    _endpoint = endpoint;
    _initOrderServicePortProxy();
  }
  
  private void _initOrderServicePortProxy() {
    try {
      orderServicePort = (new OrderServicePortServiceLocator()).getOrderServicePortSoap11();
      if (orderServicePort != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)orderServicePort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)orderServicePort)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (orderServicePort != null)
      ((javax.xml.rpc.Stub)orderServicePort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public OrderServicePort getOrderServicePort() {
    if (orderServicePort == null)
      _initOrderServicePortProxy();
    return orderServicePort;
  }
  
  public OrderFeedbackResponse orderFeedback(OrderFeedbackRequest orderFeedbackRequest) throws java.rmi.RemoteException{
    if (orderServicePort == null)
      _initOrderServicePortProxy();
    return orderServicePort.orderFeedback(orderFeedbackRequest);
  }
  
  
}