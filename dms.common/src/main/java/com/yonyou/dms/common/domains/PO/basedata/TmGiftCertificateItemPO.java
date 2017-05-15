package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("tm_gift_certificate_item")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TmGiftCertificateItemPO extends BaseModel{

}
