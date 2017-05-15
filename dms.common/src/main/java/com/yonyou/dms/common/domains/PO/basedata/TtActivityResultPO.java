package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 服务活动结果
 * @author Administrator
 *
 */
@Table("TT_ACTIVITY_RESULT")
@CompositePK({"DEALER_CODE","ACTIVITY_CODE","VIN"})
public class TtActivityResultPO extends BaseModel {

}
