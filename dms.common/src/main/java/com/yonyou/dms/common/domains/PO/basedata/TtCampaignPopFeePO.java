package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
@Table("TT_CAMPAIGN_POP_FEE")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtCampaignPopFeePO extends BaseModel{

}
