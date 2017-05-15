/**
 * 
 */
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @author sqh
 *
 */
@Table("TT_OCCUR_INSURANCE_REGISTER")
@CompositePK({"OCCUR_INSURANCE_NO","DEALER_CODE"})
public class TtOccurInsuranceRegisterPO extends BaseModel{

}
