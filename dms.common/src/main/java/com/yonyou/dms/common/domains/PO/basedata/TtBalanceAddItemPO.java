package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 结算单附加项目明细 
 * @author Administrator
 *
 */
@Table("TT_BALANCE_ADD_ITEM")
@CompositePK({"ITEM_ID","DEALER_CODE"})
public class TtBalanceAddItemPO extends BaseModel{

}
