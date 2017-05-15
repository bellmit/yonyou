package com.yonyou.dms.repair.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("tm_workgroup")
@CompositePK({"DEALER_CODE","WORKGROUP_CODE"})
public class WorkGroupPO extends BaseModel{
}
