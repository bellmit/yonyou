package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("TT_TRACE_TASK")
@CompositePK({"DEALER_CODE","RO_NO"})
public class TraceTaskPO extends BaseModel{

}
