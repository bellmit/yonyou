package com.infoeai.eai.wsClient.parts.SparePartListForPublish;

public class ZSD_SPARE_PARTProxy implements com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_PortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_PortType zSD_SPARE_PART_PortType = null;
  
  public ZSD_SPARE_PARTProxy() {
    _initZSD_SPARE_PARTProxy();
  }
  
  public ZSD_SPARE_PARTProxy(String endpoint) {
    _endpoint = endpoint;
    _initZSD_SPARE_PARTProxy();
  }
  
  private void _initZSD_SPARE_PARTProxy() {
    try {
      zSD_SPARE_PART_PortType = (new com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_ServiceLocator()).getZSD_SPARE_PART();
      if (zSD_SPARE_PART_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zSD_SPARE_PART_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zSD_SPARE_PART_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zSD_SPARE_PART_PortType != null)
      ((javax.xml.rpc.Stub)zSD_SPARE_PART_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_PortType getZSD_SPARE_PART_PortType() {
    if (zSD_SPARE_PART_PortType == null)
      _initZSD_SPARE_PARTProxy();
    return zSD_SPARE_PART_PortType;
  }
  
  public com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZrsdSpareDiff1[] zsdSparePart(com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZrsdSpareDiff1[] IDiff) throws java.rmi.RemoteException{
    if (zSD_SPARE_PART_PortType == null)
      _initZSD_SPARE_PARTProxy();
    return zSD_SPARE_PART_PortType.zsdSparePart(IDiff);
  }
  
  
}