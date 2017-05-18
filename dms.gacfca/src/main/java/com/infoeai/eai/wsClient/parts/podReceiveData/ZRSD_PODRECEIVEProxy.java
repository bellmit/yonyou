package com.infoeai.eai.wsClient.parts.podReceiveData;

public class ZRSD_PODRECEIVEProxy implements com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_PortType zRSD_PODRECEIVE_PortType = null;
  
  public ZRSD_PODRECEIVEProxy() {
    _initZRSD_PODRECEIVEProxy();
  }
  
  public ZRSD_PODRECEIVEProxy(String endpoint) {
    _endpoint = endpoint;
    _initZRSD_PODRECEIVEProxy();
  }
  
  private void _initZRSD_PODRECEIVEProxy() {
    try {
      zRSD_PODRECEIVE_PortType = (new com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_ServiceLocator()).getZRSD_PODRECEIVE();
      if (zRSD_PODRECEIVE_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zRSD_PODRECEIVE_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zRSD_PODRECEIVE_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zRSD_PODRECEIVE_PortType != null)
      ((javax.xml.rpc.Stub)zRSD_PODRECEIVE_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_PortType getZRSD_PODRECEIVE_PortType() {
    if (zRSD_PODRECEIVE_PortType == null)
      _initZRSD_PODRECEIVEProxy();
    return zRSD_PODRECEIVE_PortType;
  }
  
  public void zrsdPodreceive(com.infoeai.eai.wsClient.parts.podReceiveData.ZrsdPod IPod) throws java.rmi.RemoteException{
    if (zRSD_PODRECEIVE_PortType == null)
      _initZRSD_PODRECEIVEProxy();
    zRSD_PODRECEIVE_PortType.zrsdPodreceive(IPod);
  }
  
  
}