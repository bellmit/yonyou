package com.infoeai.eai.wsClient.parts.lvmInvoiceDetails;

public class ZLVSDWS_L28_OUTProxy implements com.infoeai.eai.wsClient.parts.lvmInvoiceDetails.ZLVSDWS_L28_OUT_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.lvmInvoiceDetails.ZLVSDWS_L28_OUT_PortType zLVSDWS_L28_OUT_PortType = null;
  
  public ZLVSDWS_L28_OUTProxy() {
    _initZLVSDWS_L28_OUTProxy();
  }
  
  public ZLVSDWS_L28_OUTProxy(String endpoint) {
    _endpoint = endpoint;
    _initZLVSDWS_L28_OUTProxy();
  }
  
  private void _initZLVSDWS_L28_OUTProxy() {
    try {
      zLVSDWS_L28_OUT_PortType = (new com.infoeai.eai.wsClient.parts.lvmInvoiceDetails.ZLVSDWS_L28_OUT_ServiceLocator()).getZLVSDWS_L28_OUT();
      if (zLVSDWS_L28_OUT_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zLVSDWS_L28_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zLVSDWS_L28_OUT_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zLVSDWS_L28_OUT_PortType != null)
      ((javax.xml.rpc.Stub)zLVSDWS_L28_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.lvmInvoiceDetails.ZLVSDWS_L28_OUT_PortType getZLVSDWS_L28_OUT_PortType() {
    if (zLVSDWS_L28_OUT_PortType == null)
      _initZLVSDWS_L28_OUTProxy();
    return zLVSDWS_L28_OUT_PortType;
  }
  
  public void zlvsdwsL28Out(java.lang.String IDealerUsr, java.lang.String[] IInvvbeln, java.lang.String IMarca, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.lvmInvoiceDetails.holders.TableOfZlvsdwsL28OutHolder tbOutputHeader, com.infoeai.eai.wsClient.parts.lvmInvoiceDetails.holders.TableOfZlvsdwsL28PosOutHolder tbOutputPosition, com.infoeai.eai.wsClient.parts.lvmInvoiceDetails.holders.TableOfBapiret1Holder tbReturn) throws java.rmi.RemoteException{
    if (zLVSDWS_L28_OUT_PortType == null)
      _initZLVSDWS_L28_OUTProxy();
    zLVSDWS_L28_OUT_PortType.zlvsdwsL28Out(IDealerUsr, IInvvbeln, IMarca, IWerks, IZzcliente, tbOutputHeader, tbOutputPosition, tbReturn);
  }
  
  
}