package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 配件销售明细
 * @author Administrator
 *
 */
@Table("tt_sales_part_item")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtSalesPartItemPO extends BaseModel {

}
