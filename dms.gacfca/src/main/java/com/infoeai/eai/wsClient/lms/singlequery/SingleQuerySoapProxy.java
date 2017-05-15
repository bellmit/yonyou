package com.infoeai.eai.wsClient.lms.singlequery;

public class SingleQuerySoapProxy implements com.infoeai.eai.wsClient.lms.singlequery.SingleQuerySoap {
  private String _endpoint = null;
  private com.infoeai.eai.wsClient.lms.singlequery.SingleQuerySoap singleQuerySoap = null;
  
  public SingleQuerySoapProxy() {
    _initSingleQuerySoapProxy();
  }
  
  public SingleQuerySoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initSingleQuerySoapProxy();
  }
  
  private void _initSingleQuerySoapProxy() {
    try {
      singleQuerySoap = (new com.infoeai.eai.wsClient.lms.singlequery.SingleQueryLocator()).getSingleQuerySoap();
      if (singleQuerySoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)singleQuerySoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)singleQuerySoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (singleQuerySoap != null)
      ((javax.xml.rpc.Stub)singleQuerySoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.infoeai.eai.wsClient.lms.singlequery.SingleQuerySoap getSingleQuerySoap() {
    if (singleQuerySoap == null)
      _initSingleQuerySoapProxy();
    return singleQuerySoap;
  }
  
  public int reSingleQuery(int ID, int conflictedType, java.lang.String DMSCustomerID, java.lang.String opportunityLevelID, java.lang.String salesConsultant) throws java.rmi.RemoteException{
    if (singleQuerySoap == null)
      _initSingleQuerySoapProxy();
    return singleQuerySoap.reSingleQuery(ID, conflictedType, DMSCustomerID, opportunityLevelID, salesConsultant);
  }
  
  public void createSingleQuery(java.lang.String dealercode, java.lang.String phone, java.lang.String telephone, javax.xml.rpc.holders.BooleanHolder createSingleQueryResult, javax.xml.rpc.holders.IntHolder conflictedType, javax.xml.rpc.holders.IntHolder opportunityLevelID, javax.xml.rpc.holders.StringHolder userName) throws java.rmi.RemoteException{
    if (singleQuerySoap == null)
      _initSingleQuerySoapProxy();
    singleQuerySoap.createSingleQuery(dealercode, phone, telephone, createSingleQueryResult, conflictedType, opportunityLevelID, userName);
  }
  
  
}