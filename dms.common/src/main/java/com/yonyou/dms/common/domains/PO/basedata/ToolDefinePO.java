package com.yonyou.dms.common.domains.PO.basedata;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
@Table("tm_tool_stock")
//@IdName("STORAGE_ID")
@CompositePK({"DEALER_CODE","TOOL_CODE"})
public class ToolDefinePO extends BaseModel{
   // DEALER_CODE, TOOL_CODE
}
