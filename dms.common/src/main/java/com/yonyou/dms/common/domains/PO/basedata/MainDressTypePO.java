package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;


@Table("tm_decrodate_group")
//@IdName("STORAGE_ID")
@CompositePK({"DEALER_CODE","MAIN_GROUP_CODE"})
public class MainDressTypePO  extends BaseModel{

}
