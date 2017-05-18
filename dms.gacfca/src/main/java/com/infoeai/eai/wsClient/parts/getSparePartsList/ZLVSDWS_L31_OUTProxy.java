package com.infoeai.eai.wsClient.parts.getSparePartsList;

public class ZLVSDWS_L31_OUTProxy implements com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_PortType zLVSDWS_L31_OUT_PortType = null;
  
  public ZLVSDWS_L31_OUTProxy() {
    _initZLVSDWS_L31_OUTProxy();
  }
  
  public ZLVSDWS_L31_OUTProxy(String endpoint) {
    _endpoint = endpoint;
    _initZLVSDWS_L31_OUTProxy();
  }
  
  private void _initZLVSDWS_L31_OUTProxy() {
    try {
      zLVSDWS_L31_OUT_PortType = (new com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_ServiceLocator()).getZLVSDWS_L31_OUT();
      if (zLVSDWS_L31_OUT_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zLVSDWS_L31_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zLVSDWS_L31_OUT_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zLVSDWS_L31_OUT_PortType != null)
      ((javax.xml.rpc.Stub)zLVSDWS_L31_OUT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_PortType getZLVSDWS_L31_OUT_PortType() {
    if (zLVSDWS_L31_OUT_PortType == null)
      _initZLVSDWS_L31_OUTProxy();
    return zLVSDWS_L31_OUT_PortType;
  }
  
  public void zlvsdwsL31Out(java.lang.String IAuart, java.lang.String IDealerUsr, java.lang.String IElcode, java.lang.String IMarca, java.lang.String IMecode, java.lang.String ISubstitute, java.lang.String IVhvin, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31MatnrHolder tbInput, com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31OutHolder tbOutput, com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfBapiret1Holder tbReturn, com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31MatHolder tbSubs) throws java.rmi.RemoteException{
    if (zLVSDWS_L31_OUT_PortType == null)
      _initZLVSDWS_L31_OUTProxy();
    zLVSDWS_L31_OUT_PortType.zlvsdwsL31Out(IAuart, IDealerUsr, IElcode, IMarca, IMecode, ISubstitute, IVhvin, IWerks, IZzcliente, tbInput, tbOutput, tbReturn, tbSubs);
  }
  
  
}