package com.yonyou.dms.common.domains.PO.stockmanage;


import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
@Table("tt_part_allocate_in")
@CompositePK({"ALLOCATE_IN_NO","DEALER_CODE"})
public class PartAllocateInPO  extends BaseModel{

}