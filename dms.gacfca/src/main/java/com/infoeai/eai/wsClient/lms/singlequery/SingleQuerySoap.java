/**
 * 
 */
package com.infoeai.eai.wsClient.lms.singlequery;

/**
 * @author Administrator
 *
 */
public interface SingleQuerySoap {
	  /**
     * DMS返回撞单查询结果
     */
    public int reSingleQuery(int ID, int conflictedType, java.lang.String DMSCustomerID, java.lang.String opportunityLevelID, java.lang.String salesConsultant) throws java.rmi.RemoteException;

    /**
     * DMS建档撞单查询
     */
    public void createSingleQuery(java.lang.String dealercode, java.lang.String phone, java.lang.String telephone, javax.xml.rpc.holders.BooleanHolder createSingleQueryResult, javax.xml.rpc.holders.IntHolder conflictedType, javax.xml.rpc.holders.IntHolder opportunityLevelID, javax.xml.rpc.holders.StringHolder userName) throws java.rmi.RemoteException;
}
