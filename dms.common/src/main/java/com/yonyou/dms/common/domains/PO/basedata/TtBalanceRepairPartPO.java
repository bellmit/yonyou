package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 结算单维修配件 
 * @author Administrator
 *
 */
@Table("TT_BALANCE_REPAIR_PART")
@CompositePK({"ITEM_ID","DEALER_CODE"})
public class TtBalanceRepairPartPO extends BaseModel {

}
