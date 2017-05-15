package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
/**
 * 工具流水账
* TODO description
* @author yujiangheng
* @date 2017年4月22日
 */
@Table("TT_TOOL_FLOW")
//@CompositePK({"ITEM_ID","DEALER_CODE"})
@IdName("FLOW_ID")
public class TtToolFlowPO extends BaseModel{

}
