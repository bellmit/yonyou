package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 收费对象结算明细统计表 
 * @author wangxin
 *
 */
@Table("TM_PAYOBJ_AMOUNT_STATISTICS")
@CompositePK({"ITEM_ID","DEALER_CODE"})
public class TmPayobjAmountStatisticsPO extends BaseModel{

}
