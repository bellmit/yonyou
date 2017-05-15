package com.infoeai.eai.wsClient.jec;

public class DmsJecServicePortTypeProxy implements com.infoeai.eai.wsClient.jec.DmsJecServicePortType {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.jec.DmsJecServicePortType dmsJecServicePortType = null;
  
  public DmsJecServicePortTypeProxy() {
    _initDmsJecServicePortTypeProxy();
  }
  
  public DmsJecServicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initDmsJecServicePortTypeProxy();
  }
  
  private void _initDmsJecServicePortTypeProxy() {
    try {
      dmsJecServicePortType = (new com.infoeai.eai.wsClient.jec.DmsJecServiceLocator()).getDmsJecServiceHttpPort();
      if (dmsJecServicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)dmsJecServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)dmsJecServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (dmsJecServicePortType != null)
      ((javax.xml.rpc.Stub)dmsJecServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.jec.DmsJecServicePortType getDmsJecServicePortType() {
    if (dmsJecServicePortType == null)
      _initDmsJecServicePortTypeProxy();
    return dmsJecServicePortType;
  }
  
  public void syncOwnerResult(java.lang.String in0) throws java.rmi.RemoteException{
    if (dmsJecServicePortType == null)
      _initDmsJecServicePortTypeProxy();
    dmsJecServicePortType.syncOwnerResult(in0);
  }
  
  public java.lang.String syncNewVehicle(java.lang.String in0) throws java.rmi.RemoteException{
    if (dmsJecServicePortType == null)
      _initDmsJecServicePortTypeProxy();
    return dmsJecServicePortType.syncNewVehicle(in0);
  }
  
  public java.lang.String addNewVehicle(java.lang.String in0) throws java.rmi.RemoteException{
    if (dmsJecServicePortType == null)
      _initDmsJecServicePortTypeProxy();
    return dmsJecServicePortType.addNewVehicle(in0);
  }
  
  public java.lang.String updateOwnerVehicle(java.lang.String in0) throws java.rmi.RemoteException{
    if (dmsJecServicePortType == null)
      _initDmsJecServicePortTypeProxy();
    return dmsJecServicePortType.updateOwnerVehicle(in0);
  }
  
  public java.lang.String addOwnerVehicle(java.lang.String in0) throws java.rmi.RemoteException{
    if (dmsJecServicePortType == null)
      _initDmsJecServicePortTypeProxy();
    return dmsJecServicePortType.addOwnerVehicle(in0);
  }
  
  public java.lang.String syncOwnerVehicle(java.lang.String in0) throws java.rmi.RemoteException{
    if (dmsJecServicePortType == null)
      _initDmsJecServicePortTypeProxy();
    return dmsJecServicePortType.syncOwnerVehicle(in0);
  }
  
  
}