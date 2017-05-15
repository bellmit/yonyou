package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 配件移库单
 * @author Administrator
 *
 */
@Table("TT_STOCK_TRANSFER")
@CompositePK({"DEALER_CODE","TRANSFER_NO"})
public class TtStockTransferPO extends BaseModel {

}
