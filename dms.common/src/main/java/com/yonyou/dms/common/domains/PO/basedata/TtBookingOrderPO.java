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
@Table("TT_BOOKING_ORDER")
@CompositePK({"DEALER_CODE","BOOKING_ORDER_NO"})
public class TtBookingOrderPO extends BaseModel{

}
