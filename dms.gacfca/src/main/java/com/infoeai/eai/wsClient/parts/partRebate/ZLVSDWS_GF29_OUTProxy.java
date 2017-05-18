package com.infoeai.eai.wsClient.parts.partRebate;

public class ZLVSDWS_GF29_OUTProxy implements com.infoeai.eai.wsClient.parts.partRebate.ZLVSDWS_GF29_OUT_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.partRebate.ZLVSDWS_GF29_OUT_PortType zLVSDWS_GF29_OUT_PortType = null;
  
  public ZLVSDWS_GF29_OUTProxy() {
    _initZLVSDWS_GF29_OUTProxy();
  }
  
  public ZLVSDWS_GF29_OUTProxy(String endpoint) {
    _endpoint = endpoint;
    _initZLVSDWS_GF29_OUTProxy();
  }
  
  private void _initZLVSDWS_GF29_OUTProxy() {
    try {
      zLVSDWS_GF29_OUT_PortType = (new com.infoeai.eai.wsClient.parts.partRebate.ZLVSDWS_GF29_OUT_ServiceLocator()).getZLVSDWS_GF29_OUT();
      if (zLVSDWS_GF29_OUT_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zLVSDWS_GF29_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zLVSDWS_GF29_OUT_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zLVSDWS_GF29_OUT_PortType != null)
      ((javax.xml.rpc.Stub)zLVSDWS_GF29_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.partRebate.ZLVSDWS_GF29_OUT_PortType getZLVSDWS_GF29_OUT_PortType() {
    if (zLVSDWS_GF29_OUT_PortType == null)
      _initZLVSDWS_GF29_OUTProxy();
    return zLVSDWS_GF29_OUT_PortType;
  }
  
  public void zlvsdwsGf29Out(com.infoeai.eai.wsClient.parts.partRebate.TpmsRDate[] IAudat, java.lang.String IBelnr, com.infoeai.eai.wsClient.parts.partRebate.ZtpmsRFldat[] IFldat, java.lang.String IType, java.lang.String IVatno, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.partRebate.holders.TableOfZlvsdwsGf29OutHolder tbOutput, com.infoeai.eai.wsClient.parts.partRebate.holders.TableOfBapiret1Holder tbReturn, javax.xml.rpc.holders.BigDecimalHolder atp, javax.xml.rpc.holders.BigDecimalHolder blocked, javax.xml.rpc.holders.BigDecimalHolder cum, javax.xml.rpc.holders.BigDecimalHolder rebate, javax.xml.rpc.holders.BigDecimalHolder saldv, javax.xml.rpc.holders.BigDecimalHolder sumin, javax.xml.rpc.holders.BigDecimalHolder sumout, javax.xml.rpc.holders.BigDecimalHolder used) throws java.rmi.RemoteException{
    if (zLVSDWS_GF29_OUT_PortType == null)
      _initZLVSDWS_GF29_OUTProxy();
    zLVSDWS_GF29_OUT_PortType.zlvsdwsGf29Out(IAudat, IBelnr, IFldat, IType, IVatno, IWerks, IZzcliente, tbOutput, tbReturn, atp, blocked, cum, rebate, saldv, sumin, sumout, used);
  }
  
  
}