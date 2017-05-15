package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* TODO description
* @author wangliang
* @date 2017年2月16日
*/
@Table("TM_SC_MODEL")
@CompositePK({"DEALER_CODE","MODEL_CODE","BRAND_CODE"})
public class TmScModelPO extends BaseModel{

}
