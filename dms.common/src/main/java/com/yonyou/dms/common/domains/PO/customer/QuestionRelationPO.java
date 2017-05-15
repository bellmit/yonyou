package com.yonyou.dms.common.domains.PO.customer;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("TT_QUESTION_RELATION")
@CompositePK({"DEALER_CODE","QUESTIONNAIRE_CODE","QUESTION_CODE"})
public class QuestionRelationPO extends BaseModel{

}
