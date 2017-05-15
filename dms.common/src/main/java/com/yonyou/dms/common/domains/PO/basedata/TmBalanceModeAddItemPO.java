package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 结算模式附加项目定义 
 * @author Administrator
 *
 */
@Table("TM_BALANCE_MODE_ADD_ITEM")
@CompositePK({"ADD_ITEM_CODE","DEALER_CODE"})
public class TmBalanceModeAddItemPO extends BaseModel{

}
