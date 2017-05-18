/**
 * ZLVSDWS_GF18_OUT_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.partCurrentAccount;

public interface ZLVSDWS_GF18_OUT_PortType extends java.rmi.Remote {
    public void zlvsdwsGf18Out(com.infoeai.eai.wsClient.parts.partCurrentAccount.TpmsRDate[] IAudat, java.lang.String IBelnr, java.lang.String IType, java.lang.String IVatno, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.partCurrentAccount.holders.TableOfBapiret1Holder tbReturn, com.infoeai.eai.wsClient.parts.partCurrentAccount.holders.TableOfZlvsdwsGf18OutHolder tboutput, javax.xml.rpc.holders.BigDecimalHolder atp, javax.xml.rpc.holders.BigDecimalHolder balance, javax.xml.rpc.holders.BigDecimalHolder blocked, javax.xml.rpc.holders.BigDecimalHolder cum, javax.xml.rpc.holders.BigDecimalHolder saldv, javax.xml.rpc.holders.BigDecimalHolder sumin, javax.xml.rpc.holders.BigDecimalHolder sumout) throws java.rmi.RemoteException;
}
