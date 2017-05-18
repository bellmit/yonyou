/**
 * ZLVSDWS_L25_OUT_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList;

public interface ZLVSDWS_L25_OUT_PortType extends java.rmi.Remote {
    public void zlvsdwsL25Out(java.lang.String IAuart, com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.TpmsRDate[] IAudat, java.lang.String IDealerUsr, java.lang.String IMarca, java.lang.String IOrdstatus, java.lang.String IVbeln, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfZlvsdwsL25OutHolder tbOutput, com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfBapiret1Holder tbReturn) throws java.rmi.RemoteException;
}
