package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
@Table("TT_CAMPAIGN_PR_FEE")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtCampaignPrFeePO extends BaseModel{

}
