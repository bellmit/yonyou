package com.infoeai.eai.wsClient.parts.ZDMS_L01_RFC;

public class ZDMS_L01_RFCProxy implements com.infoeai.eai.wsClient.parts.ZDMS_L01_RFC.ZDMS_L01_RFC_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.ZDMS_L01_RFC.ZDMS_L01_RFC_PortType zDMS_L01_RFC_PortType = null;
  
  public ZDMS_L01_RFCProxy() {
    _initZDMS_L01_RFCProxy();
  }
  
  public ZDMS_L01_RFCProxy(String endpoint) {
    _endpoint = endpoint;
    _initZDMS_L01_RFCProxy();
  }
  
  private void _initZDMS_L01_RFCProxy() {
    try {
      zDMS_L01_RFC_PortType = (new com.infoeai.eai.wsClient.parts.ZDMS_L01_RFC.ZDMS_L01_RFC_ServiceLocator()).getZDMS_L01_RFC();
      if (zDMS_L01_RFC_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zDMS_L01_RFC_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zDMS_L01_RFC_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zDMS_L01_RFC_PortType != null)
      ((javax.xml.rpc.Stub)zDMS_L01_RFC_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.ZDMS_L01_RFC.ZDMS_L01_RFC_PortType getZDMS_L01_RFC_PortType() {
    if (zDMS_L01_RFC_PortType == null)
      _initZDMS_L01_RFCProxy();
    return zDMS_L01_RFC_PortType;
  }
  
  public void zdmsL01Rfc(java.lang.String PBstnk, com.infoeai.eai.wsClient.parts.ZDMS_L01_RFC.holders.TableOfZdmsL01OutHolder tbOutput) throws java.rmi.RemoteException{
    if (zDMS_L01_RFC_PortType == null)
      _initZDMS_L01_RFCProxy();
    zDMS_L01_RFC_PortType.zdmsL01Rfc(PBstnk, tbOutput);
  }
  
  
}