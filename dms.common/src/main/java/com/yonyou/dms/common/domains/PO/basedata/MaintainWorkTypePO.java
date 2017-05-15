package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 维修工位
 * 
 * @author chenwei
 * @date 2017年3月23日
 */
@Table("TM_REPAIR_POSITION")
@CompositePK({"DEALER_CODE","LABOUR_POSITION_CODE"})
public class MaintainWorkTypePO extends BaseModel{

}
