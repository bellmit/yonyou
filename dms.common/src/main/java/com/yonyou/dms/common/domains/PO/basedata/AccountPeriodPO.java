package com.yonyou.dms.common.domains.PO.basedata;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;
import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 会计周期表
 * @author yujiangheng
 * @date 2017年3月21日
 */
@Table("tm_accounting_cycle")
//@IdName("STORAGE_ID")
@CompositePK({"DEALER_CODE","B_YEAR","PERIODS"})
public class AccountPeriodPO extends BaseModel {
}
