package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 能参加优惠活动的车辆类型
 * @author Administrator
 *
 */
@Table("TT_ACTIVITY_VEHICLE")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtActivityVehiclePO extends BaseModel {

}
