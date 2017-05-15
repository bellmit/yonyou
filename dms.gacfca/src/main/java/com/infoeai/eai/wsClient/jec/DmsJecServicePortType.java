/**
 * DmsJecServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.jec;

public interface DmsJecServicePortType extends java.rmi.Remote {
    public void syncOwnerResult(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String syncNewVehicle(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String addNewVehicle(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String updateOwnerVehicle(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String addOwnerVehicle(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String syncOwnerVehicle(java.lang.String in0) throws java.rmi.RemoteException;
}
