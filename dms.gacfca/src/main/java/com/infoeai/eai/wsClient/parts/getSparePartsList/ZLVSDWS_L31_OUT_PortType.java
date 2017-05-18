/**
 * ZLVSDWS_L31_OUT_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.getSparePartsList;

public interface ZLVSDWS_L31_OUT_PortType extends java.rmi.Remote {
    public void zlvsdwsL31Out(java.lang.String IAuart, java.lang.String IDealerUsr, java.lang.String IElcode, java.lang.String IMarca, java.lang.String IMecode, java.lang.String ISubstitute, java.lang.String IVhvin, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31MatnrHolder tbInput, com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31OutHolder tbOutput, com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfBapiret1Holder tbReturn, com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31MatHolder tbSubs) throws java.rmi.RemoteException;
}
