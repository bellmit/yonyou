package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;


/**
 * @description 结算单维修项目明细 
 * @author Administrator
 *
 */
@Table("TT_BALANCE_LABOUR")
@CompositePK({"ITEM_ID","DEALER_CODE"})
public class TtBalanceLabourPO extends BaseModel{

}
