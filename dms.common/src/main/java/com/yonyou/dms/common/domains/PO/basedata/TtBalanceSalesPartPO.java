package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 结算单销售配件明细 
 * @author Administrator
 *
 */
@Table("TT_BALANCE_SALES_PART")
@CompositePK({"ITEM_ID","DEALER_CODE"})
public class TtBalanceSalesPartPO extends BaseModel {

}
