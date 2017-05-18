package com.infoeai.eai.wsClient.parts.lvmInvoiceList;

public class ZLVSDWS_L27_OUTProxy implements com.infoeai.eai.wsClient.parts.lvmInvoiceList.ZLVSDWS_L27_OUT_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.lvmInvoiceList.ZLVSDWS_L27_OUT_PortType zLVSDWS_L27_OUT_PortType = null;
  
  public ZLVSDWS_L27_OUTProxy() {
    _initZLVSDWS_L27_OUTProxy();
  }
  
  public ZLVSDWS_L27_OUTProxy(String endpoint) {
    _endpoint = endpoint;
    _initZLVSDWS_L27_OUTProxy();
  }
  
  private void _initZLVSDWS_L27_OUTProxy() {
    try {
      zLVSDWS_L27_OUT_PortType = (new com.infoeai.eai.wsClient.parts.lvmInvoiceList.ZLVSDWS_L27_OUT_ServiceLocator()).getZLVSDWS_L27_OUT();
      if (zLVSDWS_L27_OUT_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zLVSDWS_L27_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zLVSDWS_L27_OUT_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zLVSDWS_L27_OUT_PortType != null)
      ((javax.xml.rpc.Stub)zLVSDWS_L27_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.lvmInvoiceList.ZLVSDWS_L27_OUT_PortType getZLVSDWS_L27_OUT_PortType() {
    if (zLVSDWS_L27_OUT_PortType == null)
      _initZLVSDWS_L27_OUTProxy();
    return zLVSDWS_L27_OUT_PortType;
  }
  
  public void zlvsdwsL27Out(java.lang.String IDealerUsr, com.infoeai.eai.wsClient.parts.lvmInvoiceList.TpmsRDate IInvdate, java.lang.String IInvvbeln, java.lang.String IMarca, java.lang.String IVbeln, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.lvmInvoiceList.holders.TableOfZlvsdwsL27OutHolder tbOutput, com.infoeai.eai.wsClient.parts.lvmInvoiceList.holders.TableOfBapiret1Holder tbReturn) throws java.rmi.RemoteException{
    if (zLVSDWS_L27_OUT_PortType == null)
      _initZLVSDWS_L27_OUTProxy();
    zLVSDWS_L27_OUT_PortType.zlvsdwsL27Out(IDealerUsr, IInvdate, IInvvbeln, IMarca, IVbeln, IWerks, IZzcliente, tbOutput, tbReturn);
  }
  
  
}