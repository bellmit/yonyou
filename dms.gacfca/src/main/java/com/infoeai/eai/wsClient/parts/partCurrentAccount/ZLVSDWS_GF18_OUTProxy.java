package com.infoeai.eai.wsClient.parts.partCurrentAccount;

public class ZLVSDWS_GF18_OUTProxy implements com.infoeai.eai.wsClient.parts.partCurrentAccount.ZLVSDWS_GF18_OUT_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.partCurrentAccount.ZLVSDWS_GF18_OUT_PortType zLVSDWS_GF18_OUT_PortType = null;
  
  public ZLVSDWS_GF18_OUTProxy() {
    _initZLVSDWS_GF18_OUTProxy();
  }
  
  public ZLVSDWS_GF18_OUTProxy(String endpoint) {
    _endpoint = endpoint;
    _initZLVSDWS_GF18_OUTProxy();
  }
  
  private void _initZLVSDWS_GF18_OUTProxy() {
    try {
      zLVSDWS_GF18_OUT_PortType = (new com.infoeai.eai.wsClient.parts.partCurrentAccount.ZLVSDWS_GF18_OUT_ServiceLocator()).getZLVSDWS_GF18_OUT();
      if (zLVSDWS_GF18_OUT_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zLVSDWS_GF18_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zLVSDWS_GF18_OUT_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zLVSDWS_GF18_OUT_PortType != null)
      ((javax.xml.rpc.Stub)zLVSDWS_GF18_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.partCurrentAccount.ZLVSDWS_GF18_OUT_PortType getZLVSDWS_GF18_OUT_PortType() {
    if (zLVSDWS_GF18_OUT_PortType == null)
      _initZLVSDWS_GF18_OUTProxy();
    return zLVSDWS_GF18_OUT_PortType;
  }
  
  public void zlvsdwsGf18Out(com.infoeai.eai.wsClient.parts.partCurrentAccount.TpmsRDate[] IAudat, java.lang.String IBelnr, java.lang.String IType, java.lang.String IVatno, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.partCurrentAccount.holders.TableOfBapiret1Holder tbReturn, com.infoeai.eai.wsClient.parts.partCurrentAccount.holders.TableOfZlvsdwsGf18OutHolder tboutput, javax.xml.rpc.holders.BigDecimalHolder atp, javax.xml.rpc.holders.BigDecimalHolder balance, javax.xml.rpc.holders.BigDecimalHolder blocked, javax.xml.rpc.holders.BigDecimalHolder cum, javax.xml.rpc.holders.BigDecimalHolder saldv, javax.xml.rpc.holders.BigDecimalHolder sumin, javax.xml.rpc.holders.BigDecimalHolder sumout) throws java.rmi.RemoteException{
    if (zLVSDWS_GF18_OUT_PortType == null)
      _initZLVSDWS_GF18_OUTProxy();
    zLVSDWS_GF18_OUT_PortType.zlvsdwsGf18Out(IAudat, IBelnr, IType, IVatno, IWerks, IZzcliente, tbReturn, tboutput, atp, balance, blocked, cum, saldv, sumin, sumout);
  }
  
  
}