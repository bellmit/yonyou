package com.yonyou.dms.common.domains.PO.customer;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("TT_TRACE_QUESTIONNAIRE")
@CompositePK({"DEALER_CODE","QUESTIONNAIRE_CODE"})
public class TraceQuestionnairePO extends BaseModel {

}
