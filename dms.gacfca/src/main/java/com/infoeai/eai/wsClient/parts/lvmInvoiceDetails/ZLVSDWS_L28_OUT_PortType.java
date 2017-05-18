/**
 * ZLVSDWS_L28_OUT_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.lvmInvoiceDetails;

public interface ZLVSDWS_L28_OUT_PortType extends java.rmi.Remote {
    public void zlvsdwsL28Out(java.lang.String IDealerUsr, java.lang.String[] IInvvbeln, java.lang.String IMarca, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.lvmInvoiceDetails.holders.TableOfZlvsdwsL28OutHolder tbOutputHeader, com.infoeai.eai.wsClient.parts.lvmInvoiceDetails.holders.TableOfZlvsdwsL28PosOutHolder tbOutputPosition, com.infoeai.eai.wsClient.parts.lvmInvoiceDetails.holders.TableOfBapiret1Holder tbReturn) throws java.rmi.RemoteException;
}
