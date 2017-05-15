package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 配件销售报价单明細
* TODO description
* @author chenwei
* @date 2017年5月4日
 */
@Table("TT_SALES_QUOTE_ITEM")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtSalesQuoteItemPO extends BaseModel {

}
