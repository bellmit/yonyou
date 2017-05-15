package com.yonyou.dms.common.domains.PO.basedata;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
@Table("tt_tool_buy_item")
//@IdName("STORAGE_ID")
@CompositePK({"DEALER_CODE","BUY_NO"})
public class ToolBuyItemPO extends BaseModel{
   // DEALER_CODE, TOOL_CODE
}
