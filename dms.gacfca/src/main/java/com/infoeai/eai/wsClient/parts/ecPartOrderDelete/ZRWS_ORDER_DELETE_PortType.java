/**
 * ZRWS_ORDER_DELETE_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.ecPartOrderDelete;

import com.infoeai.eai.wsClient.parts.ecPartOrderDelete.holders.ZrwsSInterfaceReturn;
import com.infoeai.eai.wsClient.parts.ecPartOrderDelete.holders.ZrwsSOrderDelete;

public interface ZRWS_ORDER_DELETE_PortType extends java.rmi.Remote {
    public ZrwsSInterfaceReturn[] zrwsOrderDelete(ZrwsSOrderDelete input) throws java.rmi.RemoteException;
}
