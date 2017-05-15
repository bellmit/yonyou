package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 预约保养
 * @author Administrator
 *
 */
@Table("TT_WX_BOOKING_MAINTAIN_PACKAGE")
@CompositePK({"DEALER_CODE","PACKAGE_CODE"})
public class TtWxBookingMaintainPackagePO extends BaseModel{

}
