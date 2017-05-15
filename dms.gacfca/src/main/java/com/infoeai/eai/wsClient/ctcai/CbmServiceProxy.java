package com.infoeai.eai.wsClient.ctcai;

public class CbmServiceProxy implements com.infoeai.eai.wsClient.ctcai.CbmService {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.ctcai.CbmService cbmService = null;
  
  public CbmServiceProxy() {
    _initCbmServiceProxy();
  }
  
  public CbmServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initCbmServiceProxy();
  }
  
  private void _initCbmServiceProxy() {
    try {
      cbmService = (new com.infoeai.eai.wsClient.ctcai.CbmServiceImplServiceLocator()).getCbmServiceImplPort();
      if (cbmService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)cbmService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)cbmService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (cbmService != null)
      ((javax.xml.rpc.Stub)cbmService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.ctcai.CbmService getCbmService() {
    if (cbmService == null)
      _initCbmServiceProxy();
    return cbmService;
  }
  
  public java.lang.String cgcslCancelOrder(java.lang.String string) throws java.rmi.RemoteException{
    if (cbmService == null)
      _initCbmServiceProxy();
    return cbmService.cgcslCancelOrder(string);
  }
  
  
}