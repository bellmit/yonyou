package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("tt_vehicle_pdi_result")
@CompositePK({"DEALER_CODE","VIN"})
public class VehiclePdiResultPO extends BaseModel{

}
