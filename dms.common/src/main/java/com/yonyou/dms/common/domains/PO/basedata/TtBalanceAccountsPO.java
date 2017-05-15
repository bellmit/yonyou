package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 结算单
 * @author Administrator
 *
 */
@Table("TT_BALANCE_ACCOUNTS")
@CompositePK({"BALANCE_NO","DEALER_CODE"})
public class TtBalanceAccountsPO extends BaseModel{

}
