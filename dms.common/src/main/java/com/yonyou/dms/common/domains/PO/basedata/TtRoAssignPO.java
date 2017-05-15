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
@Table("TT_RO_ASSIGN")
@CompositePK({"ASSIGN_ID","DEALER_CODE"})
public class TtRoAssignPO extends BaseModel {

}
