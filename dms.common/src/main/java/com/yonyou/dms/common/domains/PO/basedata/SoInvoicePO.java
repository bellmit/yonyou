package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("TT_SO_INVOICE")
@CompositePK({"ITEM_ID","DEALER_CODE"})
public class SoInvoicePO extends BaseModel {

}
