package com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList;

public class ZLVSDWS_L25_OUTProxy implements com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_PortType zLVSDWS_L25_OUT_PortType = null;
  
  public ZLVSDWS_L25_OUTProxy() {
    _initZLVSDWS_L25_OUTProxy();
  }
  
  public ZLVSDWS_L25_OUTProxy(String endpoint) {
    _endpoint = endpoint;
    _initZLVSDWS_L25_OUTProxy();
  }
  
  private void _initZLVSDWS_L25_OUTProxy() {
    try {
      zLVSDWS_L25_OUT_PortType = (new com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_ServiceLocator()).getZLVSDWS_L25_OUT();
      if (zLVSDWS_L25_OUT_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zLVSDWS_L25_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zLVSDWS_L25_OUT_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zLVSDWS_L25_OUT_PortType != null)
      ((javax.xml.rpc.Stub)zLVSDWS_L25_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_PortType getZLVSDWS_L25_OUT_PortType() {
    if (zLVSDWS_L25_OUT_PortType == null)
      _initZLVSDWS_L25_OUTProxy();
    return zLVSDWS_L25_OUT_PortType;
  }
  
  public void zlvsdwsL25Out(java.lang.String IAuart, com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.TpmsRDate[] IAudat, java.lang.String IDealerUsr, java.lang.String IMarca, java.lang.String IOrdstatus, java.lang.String IVbeln, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfZlvsdwsL25OutHolder tbOutput, com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfBapiret1Holder tbReturn) throws java.rmi.RemoteException{
    if (zLVSDWS_L25_OUT_PortType == null)
      _initZLVSDWS_L25_OUTProxy();
    zLVSDWS_L25_OUT_PortType.zlvsdwsL25Out(IAuart, IAudat, IDealerUsr, IMarca, IOrdstatus, IVbeln, IWerks, IZzcliente, tbOutput, tbReturn);
  }
  
  
}