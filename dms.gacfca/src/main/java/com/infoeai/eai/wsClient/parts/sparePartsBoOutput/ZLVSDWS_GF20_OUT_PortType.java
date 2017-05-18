/**
 * ZLVSDWS_GF20_OUT_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.sparePartsBoOutput;

public interface ZLVSDWS_GF20_OUT_PortType extends java.rmi.Remote {
    public void zlvsdwsGf20Out(com.infoeai.eai.wsClient.parts.sparePartsBoOutput.TpmsRDate[] IAudat, java.lang.String IMatnr, java.lang.String IOrdertype, java.lang.String IResolved, java.lang.String IVbeln, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.sparePartsBoOutput.holders.TableOfZlvsdwsGf20OutHolder tbOutput, com.infoeai.eai.wsClient.parts.sparePartsBoOutput.holders.TableOfBapiret1Holder tbReturn) throws java.rmi.RemoteException;
}
