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
@Table("Tt_Part_Profit")
@CompositePK({"DEALER_CODE","PROFIT_NO"})
public class TtPartProfitPO extends BaseModel {

}
