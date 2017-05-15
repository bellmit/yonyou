package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 维修操作项目PO
 * @author Administrator
 *
 */
@Table("TM_REPAIR_ITEM")
@CompositePK({"DEALER_CODE","LABOUR_CODE","MODEL_LABOUR_CODE"})
public class TmRepairItemPO extends BaseModel {

}
