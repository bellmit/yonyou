package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 货运单明细
 * @author Administrator
 *
 */
@Table("TT_PT_DELIVER_ITEM")
@IdName("ITEM_ID")
@BelongsTo(parent = TtPtDeliverPO.class,foreignKeyName = "DELIVER_ID")
public class TtPtDeliverItemPO extends BaseModel{

}
