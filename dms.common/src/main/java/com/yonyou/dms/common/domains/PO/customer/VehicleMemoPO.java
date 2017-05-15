package com.yonyou.dms.common.domains.PO.customer;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("TM_VEHICLE_MEMO")
@CompositePK({ "ITEM_ID" })
public class VehicleMemoPO extends BaseModel {

}
