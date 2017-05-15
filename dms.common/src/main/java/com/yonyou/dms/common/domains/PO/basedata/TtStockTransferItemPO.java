package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 配件移库单明细 
 * @author Administrator
 *
 */
@Table("TT_STOCK_TRANSFER_ITEM")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtStockTransferItemPO extends BaseModel {

}
