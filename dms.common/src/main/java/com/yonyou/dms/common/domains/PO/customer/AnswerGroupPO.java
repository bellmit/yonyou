package com.yonyou.dms.common.domains.PO.customer;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("TT_ANSWER_GROUP")
@CompositePK({"DEALER_CODE","ANSWER_GROUP_NO"})
public class AnswerGroupPO extends BaseModel{

}
