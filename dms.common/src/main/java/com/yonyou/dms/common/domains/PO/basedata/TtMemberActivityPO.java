/**
 * 
 */
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @author yangjie
 *
 */
@Table("TT_MEMBER_ACTIVITY")
@CompositePK({"DEALER_CODE","MEMBER_ACTIVITY_CODE"})
public class TtMemberActivityPO extends BaseModel {

}
