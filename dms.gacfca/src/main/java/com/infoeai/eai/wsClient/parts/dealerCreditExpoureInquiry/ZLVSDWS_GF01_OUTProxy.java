package com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry;

public class ZLVSDWS_GF01_OUTProxy implements com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZLVSDWS_GF01_OUT_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZLVSDWS_GF01_OUT_PortType zLVSDWS_GF01_OUT_PortType = null;
  
  public ZLVSDWS_GF01_OUTProxy() {
    _initZLVSDWS_GF01_OUTProxy();
  }
  
  public ZLVSDWS_GF01_OUTProxy(String endpoint) {
    _endpoint = endpoint;
    _initZLVSDWS_GF01_OUTProxy();
  }
  
  private void _initZLVSDWS_GF01_OUTProxy() {
    try {
      zLVSDWS_GF01_OUT_PortType = (new com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZLVSDWS_GF01_OUT_ServiceLocator()).getZLVSDWS_GF01_OUT();
      if (zLVSDWS_GF01_OUT_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zLVSDWS_GF01_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zLVSDWS_GF01_OUT_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zLVSDWS_GF01_OUT_PortType != null)
      ((javax.xml.rpc.Stub)zLVSDWS_GF01_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZLVSDWS_GF01_OUT_PortType getZLVSDWS_GF01_OUT_PortType() {
    if (zLVSDWS_GF01_OUT_PortType == null)
      _initZLVSDWS_GF01_OUTProxy();
    return zLVSDWS_GF01_OUT_PortType;
  }
  
  public void zlvsdwsGf01Out(java.lang.String ICreditConArea, java.lang.String IDealerUsr, java.lang.String IMarca, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.holders.TableOfZlvsdwsGf01OutHolder tbOutput, com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.holders.TableOfBapiret1Holder tbReturn) throws java.rmi.RemoteException{
    if (zLVSDWS_GF01_OUT_PortType == null)
      _initZLVSDWS_GF01_OUTProxy();
    zLVSDWS_GF01_OUT_PortType.zlvsdwsGf01Out(ICreditConArea, IDealerUsr, IMarca, IWerks, IZzcliente, tbOutput, tbReturn);
  }
  
  
}