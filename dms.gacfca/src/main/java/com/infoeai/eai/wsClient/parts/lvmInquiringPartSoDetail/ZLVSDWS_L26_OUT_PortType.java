/**
 * ZLVSDWS_L26_OUT_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail;

public interface ZLVSDWS_L26_OUT_PortType extends java.rmi.Remote {
    public void zlvsdwsL26Out(java.lang.String IDealerUsr, java.lang.String IMarca, java.lang.String IWerks, java.lang.String IZzcliente, com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26OutHolder tbOutputHeader, com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26PosOutHolder tbOutputPosition, com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfBapiret1Holder tbReturn, com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26VbelnHolder tbVbeln) throws java.rmi.RemoteException;
}
