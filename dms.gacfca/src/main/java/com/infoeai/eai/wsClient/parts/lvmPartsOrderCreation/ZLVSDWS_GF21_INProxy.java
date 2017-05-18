package com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation;

public class ZLVSDWS_GF21_INProxy implements com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.ZLVSDWS_GF21_IN_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.ZLVSDWS_GF21_IN_PortType zLVSDWS_GF21_IN_PortType = null;
  
  public ZLVSDWS_GF21_INProxy() {
    _initZLVSDWS_GF21_INProxy();
  }
  
  public ZLVSDWS_GF21_INProxy(String endpoint) {
    _endpoint = endpoint;
    _initZLVSDWS_GF21_INProxy();
  }
  
  private void _initZLVSDWS_GF21_INProxy() {
    try {
      zLVSDWS_GF21_IN_PortType = (new com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.ZLVSDWS_GF21_IN_ServiceLocator()).getZLVSDWS_GF21_IN();
      if (zLVSDWS_GF21_IN_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zLVSDWS_GF21_IN_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zLVSDWS_GF21_IN_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zLVSDWS_GF21_IN_PortType != null)
      ((javax.xml.rpc.Stub)zLVSDWS_GF21_IN_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.ZLVSDWS_GF21_IN_PortType getZLVSDWS_GF21_IN_PortType() {
    if (zLVSDWS_GF21_IN_PortType == null)
      _initZLVSDWS_GF21_INProxy();
    return zLVSDWS_GF21_IN_PortType;
  }
  
  public java.lang.String zlvsdwsGf21In(java.lang.String IChange, java.lang.String IDealerUsr, java.lang.String IElecCode, java.lang.String IEmerg, java.lang.String IKeyCode, java.lang.String IMarca, java.lang.String IMechCode, java.lang.String IName1, java.lang.String INote, java.lang.String IOrderType, java.lang.String IRefNumber, java.lang.String ISigni, java.lang.String ITel, java.lang.String IVinCode, java.lang.String IWerks, java.lang.String IYj, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.holders.TableOfZbapiret11Holder tbBapireturn, com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.holders.TableOfZlvsdwsGf21InHolder tbInput, com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.holders.TableOfZlvsdwsGf21PriHolder tbOutput, com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.holders.TableOfBapiret1Holder tbReturn) throws java.rmi.RemoteException{
    if (zLVSDWS_GF21_IN_PortType == null)
      _initZLVSDWS_GF21_INProxy();
    return zLVSDWS_GF21_IN_PortType.zlvsdwsGf21In(IChange, IDealerUsr, IElecCode, IEmerg, IKeyCode, IMarca, IMechCode, IName1, INote, IOrderType, IRefNumber, ISigni, ITel, IVinCode, IWerks, IYj, IZzcliente, tbBapireturn, tbInput, tbOutput, tbReturn);
  }
  
  
}