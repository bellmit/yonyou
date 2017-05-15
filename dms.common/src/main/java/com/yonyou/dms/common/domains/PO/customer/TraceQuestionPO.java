package com.yonyou.dms.common.domains.PO.customer;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("TT_TRACE_QUESTION")
@CompositePK({"DEALER_CODE","QUESTION_CODE"})
public class TraceQuestionPO extends BaseModel {

}
