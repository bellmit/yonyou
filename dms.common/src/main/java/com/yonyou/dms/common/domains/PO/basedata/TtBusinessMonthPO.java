package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;


@Table("Tt_Business_Month")
@CompositePK({"dealer_code","BUSINESS_YEAR","BUSINESS_MONTH"})
public class TtBusinessMonthPO extends BaseModel{

}
