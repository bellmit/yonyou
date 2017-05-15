package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("tm_onduty_enregister")
@CompositePK({"DEALER_CODE","REGISTER_DATE","EMPLOYEE_NO"})
public class DutyRecordPO extends BaseModel{

}
