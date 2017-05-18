/**
 * ZLVSDWS_SJV01_OUT_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.dealerDeliveryDocList;

public interface ZLVSDWS_SJV01_OUT_PortType extends java.rmi.Remote {
    public void zlvsdwsSjv01Out(java.lang.String dateFrom, java.lang.String dateTo, com.infoeai.eai.wsClient.parts.dealerDeliveryDocList.holders.TableOfZlvsdwsSjv01VbelnHolder tbInputVbeln, com.infoeai.eai.wsClient.parts.dealerDeliveryDocList.holders.TableOfZlvsdwsSjv01HeadOutHolder tbOutputHeader, com.infoeai.eai.wsClient.parts.dealerDeliveryDocList.holders.TableOfZlvsdwsSjv01ItemOutHolder tbOutputPosition, com.infoeai.eai.wsClient.parts.dealerDeliveryDocList.holders.TableOfBapiret1Holder tbReturn, java.lang.String timeFrom, java.lang.String timeTo) throws java.rmi.RemoteException;
}
