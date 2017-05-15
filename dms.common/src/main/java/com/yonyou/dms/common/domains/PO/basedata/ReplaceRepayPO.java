package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
@Table("tt_replace_repay")
@CompositePK({"DEALER_CODE","SO_NO"})
public class ReplaceRepayPO extends BaseModel{

}
