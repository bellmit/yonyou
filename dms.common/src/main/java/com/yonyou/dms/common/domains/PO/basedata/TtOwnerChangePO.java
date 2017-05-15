package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("tt_owner_change")
@CompositePK({"ITEM_ID","DEALER_CODE"})
public class TtOwnerChangePO extends BaseModel{
	
	
}
