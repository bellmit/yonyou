/**
 * ZLVSDWS_L27_OUT_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.lvmInvoiceList;

public interface ZLVSDWS_L27_OUT_PortType extends java.rmi.Remote {
    public void zlvsdwsL27Out(java.lang.String IDealerUsr, com.infoeai.eai.wsClient.parts.lvmInvoiceList.TpmsRDate IInvdate, java.lang.String IInvvbeln, java.lang.String IMarca, java.lang.String IVbeln, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.lvmInvoiceList.holders.TableOfZlvsdwsL27OutHolder tbOutput, com.infoeai.eai.wsClient.parts.lvmInvoiceList.holders.TableOfBapiret1Holder tbReturn) throws java.rmi.RemoteException;
}
