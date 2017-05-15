package com.yonyou.dms.common.domains.PO.basedata;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;
import com.yonyou.dms.framework.domain.BaseModel;
@Table("TT_PT_DELIVER_ITEM")
@CompositePK({"DEALER_CODE","PSO_NO"})
//@IdName("ITEM_ID")
public class TtPartSignOrderPO extends BaseModel{

}
