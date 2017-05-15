package com.yonyou.dms.common.domains.PO.basedata;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;
import com.yonyou.dms.framework.domain.BaseModel;
@Table("tm_unit")
@CompositePK({"UNIT_CODE","DEALER_CODE"}) //联合主键
public class UnitPo extends BaseModel {
}
