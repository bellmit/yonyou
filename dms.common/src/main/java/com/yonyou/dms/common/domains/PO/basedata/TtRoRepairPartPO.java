package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
/**
 * 工单维修配件明细
* TODO description
* @author chenwei
* @date 2017年4月7日
 */
@Table("TT_RO_REPAIR_PART")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtRoRepairPartPO extends BaseModel{

}