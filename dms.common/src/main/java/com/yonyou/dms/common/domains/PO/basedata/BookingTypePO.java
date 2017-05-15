package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
@Table("TM_BOOKING_TYPE")
@CompositePK({"DEALER_CODE","BOOKING_TYPE_CODE"})
public class BookingTypePO extends BaseModel{

}
