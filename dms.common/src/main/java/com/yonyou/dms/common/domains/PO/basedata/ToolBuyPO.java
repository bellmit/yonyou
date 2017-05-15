package com.yonyou.dms.common.domains.PO.basedata;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
@Table("tt_tool_buy")
//@IdName("STORAGE_ID")
@CompositePK({"BUY_NO","DEALER_CODE"})
public class ToolBuyPO extends BaseModel{
   // DEALER_CODE, TOOL_CODE
}
