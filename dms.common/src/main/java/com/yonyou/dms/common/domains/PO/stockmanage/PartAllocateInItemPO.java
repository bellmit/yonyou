package com.yonyou.dms.common.domains.PO.stockmanage;

import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("tt_part_allocate_in_item")
@IdName("ITEM_ID")
@BelongsTo(parent = PartAllocateInPO.class, foreignKeyName = "F_P_ALLOCATE_IN_I")
public class PartAllocateInItemPO extends BaseModel{

}