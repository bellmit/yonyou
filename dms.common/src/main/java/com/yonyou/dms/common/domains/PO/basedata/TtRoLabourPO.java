package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 工单维修项目明细 
 * @author Administrator
 *
 */
@Table("TT_RO_LABOUR")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtRoLabourPO extends BaseModel{

}
