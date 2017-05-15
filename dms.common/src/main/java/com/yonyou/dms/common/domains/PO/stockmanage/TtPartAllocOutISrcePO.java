package com.yonyou.dms.common.domains.PO.stockmanage;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("tt_part_alloc_out_i_srce")
@IdName("ITEM_ID")
@BelongsTo(parent = TtPartAllocateOutSourcePO.class, foreignKeyName = "F_PART_ALLO_O_SRC")
public class TtPartAllocOutISrcePO extends BaseModel {

}
