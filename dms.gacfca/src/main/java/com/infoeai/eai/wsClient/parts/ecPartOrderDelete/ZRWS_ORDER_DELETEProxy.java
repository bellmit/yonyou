package com.infoeai.eai.wsClient.parts.ecPartOrderDelete;

import com.infoeai.eai.wsClient.parts.ecPartOrderDelete.holders.ZrwsSInterfaceReturn;
import com.infoeai.eai.wsClient.parts.ecPartOrderDelete.holders.ZrwsSOrderDelete;

public class ZRWS_ORDER_DELETEProxy implements ZRWS_ORDER_DELETE_PortType {
  private String _endpoint = null;
  private ZRWS_ORDER_DELETE_PortType zRWS_ORDER_DELETE_PortType = null;
  
  public ZRWS_ORDER_DELETEProxy() {
    _initZRWS_ORDER_DELETEProxy();
  }
  
  public ZRWS_ORDER_DELETEProxy(String endpoint) {
    _endpoint = endpoint;
    _initZRWS_ORDER_DELETEProxy();
  }
  
  private void _initZRWS_ORDER_DELETEProxy() {
    try {
      zRWS_ORDER_DELETE_PortType = (new ZRWS_ORDER_DELETE_ServiceLocator()).getZRWS_ORDER_DELETE();
      if (zRWS_ORDER_DELETE_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zRWS_ORDER_DELETE_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zRWS_ORDER_DELETE_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zRWS_ORDER_DELETE_PortType != null)
      ((javax.xml.rpc.Stub)zRWS_ORDER_DELETE_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ZRWS_ORDER_DELETE_PortType getZRWS_ORDER_DELETE_PortType() {
    if (zRWS_ORDER_DELETE_PortType == null)
      _initZRWS_ORDER_DELETEProxy();
    return zRWS_ORDER_DELETE_PortType;
  }
  
  public ZrwsSInterfaceReturn[] zrwsOrderDelete(ZrwsSOrderDelete input) throws java.rmi.RemoteException{
    if (zRWS_ORDER_DELETE_PortType == null)
      _initZRWS_ORDER_DELETEProxy();
    return zRWS_ORDER_DELETE_PortType.zrwsOrderDelete(input);
  }
  
  
}