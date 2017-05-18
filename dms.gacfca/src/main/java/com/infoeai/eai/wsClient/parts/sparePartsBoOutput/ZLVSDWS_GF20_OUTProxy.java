package com.infoeai.eai.wsClient.parts.sparePartsBoOutput;

public class ZLVSDWS_GF20_OUTProxy implements com.infoeai.eai.wsClient.parts.sparePartsBoOutput.ZLVSDWS_GF20_OUT_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.sparePartsBoOutput.ZLVSDWS_GF20_OUT_PortType zLVSDWS_GF20_OUT_PortType = null;
  
  public ZLVSDWS_GF20_OUTProxy() {
    _initZLVSDWS_GF20_OUTProxy();
  }
  
  public ZLVSDWS_GF20_OUTProxy(String endpoint) {
    _endpoint = endpoint;
    _initZLVSDWS_GF20_OUTProxy();
  }
  
  private void _initZLVSDWS_GF20_OUTProxy() {
    try {
      zLVSDWS_GF20_OUT_PortType = (new com.infoeai.eai.wsClient.parts.sparePartsBoOutput.ZLVSDWS_GF20_OUT_ServiceLocator()).getZLVSDWS_GF20_OUT();
      if (zLVSDWS_GF20_OUT_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zLVSDWS_GF20_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zLVSDWS_GF20_OUT_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zLVSDWS_GF20_OUT_PortType != null)
      ((javax.xml.rpc.Stub)zLVSDWS_GF20_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.sparePartsBoOutput.ZLVSDWS_GF20_OUT_PortType getZLVSDWS_GF20_OUT_PortType() {
    if (zLVSDWS_GF20_OUT_PortType == null)
      _initZLVSDWS_GF20_OUTProxy();
    return zLVSDWS_GF20_OUT_PortType;
  }
  
  public void zlvsdwsGf20Out(com.infoeai.eai.wsClient.parts.sparePartsBoOutput.TpmsRDate[] IAudat, java.lang.String IMatnr, java.lang.String IOrdertype, java.lang.String IResolved, java.lang.String IVbeln, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.sparePartsBoOutput.holders.TableOfZlvsdwsGf20OutHolder tbOutput, com.infoeai.eai.wsClient.parts.sparePartsBoOutput.holders.TableOfBapiret1Holder tbReturn) throws java.rmi.RemoteException{
    if (zLVSDWS_GF20_OUT_PortType == null)
      _initZLVSDWS_GF20_OUTProxy();
    zLVSDWS_GF20_OUT_PortType.zlvsdwsGf20Out(IAudat, IMatnr, IOrdertype, IResolved, IVbeln, IWerks, IZzcliente, tbOutput, tbReturn);
  }
  
  
}