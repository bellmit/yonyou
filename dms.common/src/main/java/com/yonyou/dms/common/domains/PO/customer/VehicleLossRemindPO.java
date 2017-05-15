/**
 * 
 */
package com.yonyou.dms.common.domains.PO.customer;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @author sqh
 *
 */
@Table("tt_vehicle_loss_remind")
@CompositePK({ "DEALER_CODE", "REMIND_ID" })
public class VehicleLossRemindPO  extends BaseModel{

}
