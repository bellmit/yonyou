package com.infoeai.eai.wsClient.parts.ecPartStock;

public class ZRWS_PARTS_STOCKProxy implements com.infoeai.eai.wsClient.parts.ecPartStock.ZRWS_PARTS_STOCK_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.ecPartStock.ZRWS_PARTS_STOCK_PortType zRWS_PARTS_STOCK_PortType = null;
  
  public ZRWS_PARTS_STOCKProxy() {
    _initZRWS_PARTS_STOCKProxy();
  }
  
  public ZRWS_PARTS_STOCKProxy(String endpoint) {
    _endpoint = endpoint;
    _initZRWS_PARTS_STOCKProxy();
  }
  
  private void _initZRWS_PARTS_STOCKProxy() {
    try {
      zRWS_PARTS_STOCK_PortType = (new com.infoeai.eai.wsClient.parts.ecPartStock.ZRWS_PARTS_STOCK_ServiceLocator()).getZRWS_PARTS_STOCK();
      if (zRWS_PARTS_STOCK_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zRWS_PARTS_STOCK_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zRWS_PARTS_STOCK_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zRWS_PARTS_STOCK_PortType != null)
      ((javax.xml.rpc.Stub)zRWS_PARTS_STOCK_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.ecPartStock.ZRWS_PARTS_STOCK_PortType getZRWS_PARTS_STOCK_PortType() {
    if (zRWS_PARTS_STOCK_PortType == null)
      _initZRWS_PARTS_STOCKProxy();
    return zRWS_PARTS_STOCK_PortType;
  }
  
  public void zrwsPartsStock(com.infoeai.eai.wsClient.parts.ecPartStock.ZrwsSMaterielList[] tbInput, com.infoeai.eai.wsClient.parts.ecPartStock.holders.ZrwsTMaterielOutputHolder tbOutput, com.infoeai.eai.wsClient.parts.ecPartStock.holders.ZrwsTInterfaceReturnHolder tbReturn) throws java.rmi.RemoteException{
    if (zRWS_PARTS_STOCK_PortType == null)
      _initZRWS_PARTS_STOCKProxy();
    zRWS_PARTS_STOCK_PortType.zrwsPartsStock(tbInput, tbOutput, tbReturn);
  }
  
  
}