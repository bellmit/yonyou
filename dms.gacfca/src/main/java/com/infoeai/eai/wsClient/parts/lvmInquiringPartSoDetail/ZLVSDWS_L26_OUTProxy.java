package com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail;

public class ZLVSDWS_L26_OUTProxy implements com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_PortType zLVSDWS_L26_OUT_PortType = null;
  
  public ZLVSDWS_L26_OUTProxy() {
    _initZLVSDWS_L26_OUTProxy();
  }
  
  public ZLVSDWS_L26_OUTProxy(String endpoint) {
    _endpoint = endpoint;
    _initZLVSDWS_L26_OUTProxy();
  }
  
  private void _initZLVSDWS_L26_OUTProxy() {
    try {
      zLVSDWS_L26_OUT_PortType = (new com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_ServiceLocator()).getZLVSDWS_L26_OUT();
      if (zLVSDWS_L26_OUT_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zLVSDWS_L26_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zLVSDWS_L26_OUT_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zLVSDWS_L26_OUT_PortType != null)
      ((javax.xml.rpc.Stub)zLVSDWS_L26_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_PortType getZLVSDWS_L26_OUT_PortType() {
    if (zLVSDWS_L26_OUT_PortType == null)
      _initZLVSDWS_L26_OUTProxy();
    return zLVSDWS_L26_OUT_PortType;
  }
  
  public void zlvsdwsL26Out(java.lang.String IDealerUsr, java.lang.String IMarca, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26OutHolder tbOutputHeader, com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26PosOutHolder tbOutputPosition, com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfBapiret1Holder tbReturn, com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26VbelnHolder tbVbeln) throws java.rmi.RemoteException{
    if (zLVSDWS_L26_OUT_PortType == null)
      _initZLVSDWS_L26_OUTProxy();
    zLVSDWS_L26_OUT_PortType.zlvsdwsL26Out(IDealerUsr, IMarca, IWerks, IZzcliente, tbOutputHeader, tbOutputPosition, tbReturn, tbVbeln);
  }
  
  
}