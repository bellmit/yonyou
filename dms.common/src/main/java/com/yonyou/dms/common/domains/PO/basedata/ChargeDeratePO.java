package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("TT_CHARGE_DERATE")
@CompositePK({"DEALER_CODE","DERATE_NO"})
public class ChargeDeratePO extends BaseModel{

}


