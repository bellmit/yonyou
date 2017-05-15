package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
@Table("TM_DECRODATE_ITEM")
@CompositePK({"DEALER_CODE","LABOUR_CODE","MODEL_LABOUR_CODE"})
public class DecrodateItemPO extends BaseModel{

}
