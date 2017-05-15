package com.yonyou.dms.common.domains.PO.customer;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
@Table("TT_ANSWER")
@CompositePK({"DEALER_CODE","ANSWER_NO"})
public class AnswerPO extends BaseModel{

}
