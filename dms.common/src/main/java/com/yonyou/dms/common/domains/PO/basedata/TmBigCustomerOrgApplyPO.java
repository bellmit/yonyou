package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("TM_BIG_CUSTOMER_ORG_APPLY")
@CompositePK({"DEALER_CODE","APPLY_NO"})
public class TmBigCustomerOrgApplyPO extends BaseModel{

}
