package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 故障描述
 * 
 * @author chenwei
 * @date 2017年3月24日
 */
@Table("TM_TROUBLE")
@CompositePK({"DEALER_CODE","TROUBLE_CODE"})
public class TroubleDescPO extends BaseModel{

}
