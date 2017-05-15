package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* @description 货运单主表PO
* @author Administrator
*/
@Table("TT_PT_DELIVER")
@CompositePK({"DEALER_CODE","ORDER_REGEDIT_NO","DELIVERY_TIME"})
public class TtPtDeliverPO extends BaseModel{

}
