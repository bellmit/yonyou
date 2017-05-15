/**
 * 
 */
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @author Administrator
 *
 */
@Table("tm_customer_family_person")
@CompositePK({"CONNECTION_CODE","DEALER_CODE","PERSON_NAME"})
public class TmCustomerFamilyPersonPO extends BaseModel {

}
