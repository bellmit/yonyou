/**
 * ZLVSDWS_GF29_OUT_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.partRebate;

public interface ZLVSDWS_GF29_OUT_PortType extends java.rmi.Remote {
    public void zlvsdwsGf29Out(com.infoeai.eai.wsClient.parts.partRebate.TpmsRDate[] IAudat, java.lang.String IBelnr, com.infoeai.eai.wsClient.parts.partRebate.ZtpmsRFldat[] IFldat, java.lang.String IType, java.lang.String IVatno, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.partRebate.holders.TableOfZlvsdwsGf29OutHolder tbOutput, com.infoeai.eai.wsClient.parts.partRebate.holders.TableOfBapiret1Holder tbReturn, javax.xml.rpc.holders.BigDecimalHolder atp, javax.xml.rpc.holders.BigDecimalHolder blocked, javax.xml.rpc.holders.BigDecimalHolder cum, javax.xml.rpc.holders.BigDecimalHolder rebate, javax.xml.rpc.holders.BigDecimalHolder saldv, javax.xml.rpc.holders.BigDecimalHolder sumin, javax.xml.rpc.holders.BigDecimalHolder sumout, javax.xml.rpc.holders.BigDecimalHolder used) throws java.rmi.RemoteException;
}
