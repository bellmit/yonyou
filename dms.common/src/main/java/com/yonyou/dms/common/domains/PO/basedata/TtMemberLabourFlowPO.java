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
@Table("TT_MEMBER_LABOUR_FLOW")
@CompositePK({"ITEM_ID","DEALER_CODE"})
public class TtMemberLabourFlowPO extends BaseModel {

}
