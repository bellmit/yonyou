/**
 * 
 */
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @author yangjie
 *
 */
@Table("TM_MICROMSG_BOOKING_ORDER")
@CompositePK({"RESERVATION_ID","DEALER_CODE"})
public class TmMicromsgBookingOrderPO extends BaseModel{

}
