package com.infoeai.eai.wsClient.parts.ecPartOrder;

import com.infoeai.eai.wsClient.parts.ecPartOrder.holders.ZrwsSOrderOutputHolder;
import com.infoeai.eai.wsClient.parts.ecPartOrder.holders.ZrwsTInterfaceReturnHolder;

public class ZRWS_PARTS_ORDERProxy implements ZRWS_PARTS_ORDER_PortType {
  private String _endpoint = null;
  private ZRWS_PARTS_ORDER_PortType zRWS_PARTS_ORDER_PortType = null;
  
  public ZRWS_PARTS_ORDERProxy() {
    _initZRWS_PARTS_ORDERProxy();
  }
  
  public ZRWS_PARTS_ORDERProxy(String endpoint) {
    _endpoint = endpoint;
    _initZRWS_PARTS_ORDERProxy();
  }
  
  private void _initZRWS_PARTS_ORDERProxy() {
    try {
      zRWS_PARTS_ORDER_PortType = (new ZRWS_PARTS_ORDER_ServiceLocator()).getZRWS_PARTS_ORDER();
      if (zRWS_PARTS_ORDER_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zRWS_PARTS_ORDER_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zRWS_PARTS_ORDER_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zRWS_PARTS_ORDER_PortType != null)
      ((javax.xml.rpc.Stub)zRWS_PARTS_ORDER_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ZRWS_PARTS_ORDER_PortType getZRWS_PARTS_ORDER_PortType() {
    if (zRWS_PARTS_ORDER_PortType == null)
      _initZRWS_PARTS_ORDERProxy();
    return zRWS_PARTS_ORDER_PortType;
  }
  
  public void zrwsPartsOrder(java.lang.String IChange, java.lang.String ICusAddr, java.lang.String ICusName, java.lang.String ICusTel, java.lang.String IDealerUsr, java.lang.String IElecCode, java.lang.String IEmerg, java.lang.String IKeyCode, java.lang.String IMarca, java.lang.String IMechCode, java.lang.String IName1, java.lang.String INote, java.lang.String IOrderType, java.lang.String IRefNumber, java.lang.String IRemark, java.lang.String ISigni, java.lang.String ITel, java.lang.String IVinCode, java.lang.String IWerks, java.lang.String IYj, java.lang.String IZzcliente, ZrwsSOrderInput[] tbInput, ZrwsSOrderOutputHolder tbOutput, ZrwsTInterfaceReturnHolder tbReturn) throws java.rmi.RemoteException{
    if (zRWS_PARTS_ORDER_PortType == null)
      _initZRWS_PARTS_ORDERProxy();
    zRWS_PARTS_ORDER_PortType.zrwsPartsOrder(IChange, ICusAddr, ICusName, ICusTel, IDealerUsr, IElecCode, IEmerg, IKeyCode, IMarca, IMechCode, IName1, INote, IOrderType, IRefNumber, IRemark, ISigni, ITel, IVinCode, IWerks, IYj, IZzcliente, tbInput, tbOutput, tbReturn);
  }
  
  
}