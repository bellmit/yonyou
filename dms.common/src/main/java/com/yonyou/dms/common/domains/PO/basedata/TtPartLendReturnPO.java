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
@Table("TT_PART_LEND_RETURN")
@CompositePK({"DEALER_CODE","RETURN_NO"})
public class TtPartLendReturnPO extends BaseModel {

}
