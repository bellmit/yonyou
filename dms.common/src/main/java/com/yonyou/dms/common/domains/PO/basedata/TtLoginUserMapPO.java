package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
@Table("TT_LOGIN_USER_MAP")
@CompositePK({"DEALER_CODE","USER_CODE"})
public class TtLoginUserMapPO extends BaseModel{

}
