package com.yonyou.dms.common.domains.PO.stockmanage;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
@Table("tt_part_allocate_out_source")
@CompositePK({"DEALER_CODE","ALLOCATE_OUT_NO","FROM_ENTITY"})
public class TtPartAllocateOutSourcePO extends BaseModel{

}
