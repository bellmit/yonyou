/**
 * 
 */
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @author sqh
 *
 */
@Table("TM_MEMBER_VEHICLE")
@CompositePK({ "DEALER_CODE", "ITEM_ID" })
public class TmMemberVehiclePO extends BaseModel{

}
