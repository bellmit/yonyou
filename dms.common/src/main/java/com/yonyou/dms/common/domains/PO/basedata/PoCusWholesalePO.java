package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("tt_po_cus_wholesale")
@CompositePK({"DEALER_CODE","WS_NO"})
public class PoCusWholesalePO extends BaseModel {

}
