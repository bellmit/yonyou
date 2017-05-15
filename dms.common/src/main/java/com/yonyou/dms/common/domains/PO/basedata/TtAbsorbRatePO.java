package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 吸收率上报表
 * @author Administrator
 *
 */
@Table("TT_ABSORB_RATE")
@CompositePK({"DEALER_CODE", "ABSORB_YEAR", "ABSORB_MONTH"})
public class TtAbsorbRatePO extends BaseModel{

}
