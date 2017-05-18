/**
 * ZRWS_PARTS_ORDER_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.ecPartOrder;

import com.infoeai.eai.wsClient.parts.ecPartOrder.holders.ZrwsSOrderOutputHolder;
import com.infoeai.eai.wsClient.parts.ecPartOrder.holders.ZrwsTInterfaceReturnHolder;

public interface ZRWS_PARTS_ORDER_PortType extends java.rmi.Remote {
    public void zrwsPartsOrder(java.lang.String IChange, java.lang.String ICusAddr, java.lang.String ICusName, java.lang.String ICusTel, java.lang.String IDealerUsr, java.lang.String IElecCode, java.lang.String IEmerg, java.lang.String IKeyCode, java.lang.String IMarca, java.lang.String IMechCode, java.lang.String IName1, java.lang.String INote, java.lang.String IOrderType, java.lang.String IRefNumber, java.lang.String IRemark, java.lang.String ISigni, java.lang.String ITel, java.lang.String IVinCode, java.lang.String IWerks, java.lang.String IYj, java.lang.String IZzcliente, ZrwsSOrderInput[] tbInput, ZrwsSOrderOutputHolder tbOutput, ZrwsTInterfaceReturnHolder tbReturn) throws java.rmi.RemoteException;
}
