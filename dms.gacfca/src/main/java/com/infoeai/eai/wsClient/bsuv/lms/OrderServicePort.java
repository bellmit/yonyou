/**
 * OrderServicePort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.bsuv.lms;

public interface OrderServicePort extends java.rmi.Remote {
    public Customer[] getTypeBTimeOut(GetTypeBTimeOutRequest getTypeBTimeOutRequest) throws java.rmi.RemoteException;
    public DepositVerificationResponse depositVerification(DepositVerificationRequest depositVerificationRequest) throws java.rmi.RemoteException;
    public Order[] getOrders(GetOrdersRequest getOrdersRequest) throws java.rmi.RemoteException;
    public SaleLead[] getSaleLeads(GetSaleLeadsRequest getSaleLeadsRequest) throws java.rmi.RemoteException;
}
