package com.infoeai.eai.wsClient.parts.dealerDeliveryDocList;

public class ZLVSDWS_SJV01_OUTProxy implements com.infoeai.eai.wsClient.parts.dealerDeliveryDocList.ZLVSDWS_SJV01_OUT_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.dealerDeliveryDocList.ZLVSDWS_SJV01_OUT_PortType zLVSDWS_SJV01_OUT_PortType = null;
  
  public ZLVSDWS_SJV01_OUTProxy() {
    _initZLVSDWS_SJV01_OUTProxy();
  }
  
  public ZLVSDWS_SJV01_OUTProxy(String endpoint) {
    _endpoint = endpoint;
    _initZLVSDWS_SJV01_OUTProxy();
  }
  
  private void _initZLVSDWS_SJV01_OUTProxy() {
    try {
      zLVSDWS_SJV01_OUT_PortType = (new com.infoeai.eai.wsClient.parts.dealerDeliveryDocList.ZLVSDWS_SJV01_OUT_ServiceLocator()).getZLVSDWS_SJV01_OUT();
      if (zLVSDWS_SJV01_OUT_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zLVSDWS_SJV01_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zLVSDWS_SJV01_OUT_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zLVSDWS_SJV01_OUT_PortType != null)
      ((javax.xml.rpc.Stub)zLVSDWS_SJV01_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.dealerDeliveryDocList.ZLVSDWS_SJV01_OUT_PortType getZLVSDWS_SJV01_OUT_PortType() {
    if (zLVSDWS_SJV01_OUT_PortType == null)
      _initZLVSDWS_SJV01_OUTProxy();
    return zLVSDWS_SJV01_OUT_PortType;
  }
  
  public void zlvsdwsSjv01Out(java.lang.String dateFrom, java.lang.String dateTo, com.infoeai.eai.wsClient.parts.dealerDeliveryDocList.holders.TableOfZlvsdwsSjv01VbelnHolder tbInputVbeln, com.infoeai.eai.wsClient.parts.dealerDeliveryDocList.holders.TableOfZlvsdwsSjv01HeadOutHolder tbOutputHeader, com.infoeai.eai.wsClient.parts.dealerDeliveryDocList.holders.TableOfZlvsdwsSjv01ItemOutHolder tbOutputPosition, com.infoeai.eai.wsClient.parts.dealerDeliveryDocList.holders.TableOfBapiret1Holder tbReturn, java.lang.String timeFrom, java.lang.String timeTo) throws java.rmi.RemoteException{
    if (zLVSDWS_SJV01_OUT_PortType == null)
      _initZLVSDWS_SJV01_OUTProxy();
    zLVSDWS_SJV01_OUT_PortType.zlvsdwsSjv01Out(dateFrom, dateTo, tbInputVbeln, tbOutputHeader, tbOutputPosition, tbReturn, timeFrom, timeTo);
  }
  
  
}