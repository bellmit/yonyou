package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("TM_ENTITY_DEALER_CHANGE")
@CompositePK({"ENTITY_CODE","DEALER_CODE"})
public class EntityDealerChangePO extends BaseModel{

}
