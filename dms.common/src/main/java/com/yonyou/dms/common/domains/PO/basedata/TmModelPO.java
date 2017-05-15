package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("TM_MODEL")
@CompositePK({"DEALER_CODE","BRAND_CODE","SERIES_CODE","MODEL_CODE"})
public class TmModelPO extends BaseModel {

}
