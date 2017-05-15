package com.yonyou.dms.common.domains.PO.basedata;
import org.javalite.activejdbc.annotations.CompositePK;
//import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;
import com.yonyou.dms.framework.domain.BaseModel;
@Table("tm_other_cost")
//@IdName("ITEM_ID")
@CompositePK({"OTHER_COST_CODE","DEALER_CODE"})
public class OtherCostDefinePO extends BaseModel{
}
