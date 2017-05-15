package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
@Table("tm_tool_type")
@CompositePK({"DEALER_CODE","TOOL_TYPE_CODE"})
public class ToolsTypePO extends BaseModel{
}
