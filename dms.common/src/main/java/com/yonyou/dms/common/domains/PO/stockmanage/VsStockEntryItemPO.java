package com.yonyou.dms.common.domains.PO.stockmanage;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("tt_vs_stock_entry_item")
@CompositePK({ "DEALER_CODE", "SE_NO", "VIN" })
public class VsStockEntryItemPO extends BaseModel {

}
