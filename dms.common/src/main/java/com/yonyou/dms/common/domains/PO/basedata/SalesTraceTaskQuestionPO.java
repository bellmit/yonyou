package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("TT_SALES_TRACE_TASK_QUESTION")
@CompositePK({ "DEALER_CODE","TRACE_TASK_ID","TRACE_TASK_QUESTION_ID"})
public class SalesTraceTaskQuestionPO extends BaseModel {

}
