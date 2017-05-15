package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
@Table("TT_CAMPAIGN_PSALES_FEE")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtCampaignPsalesFeePO extends BaseModel{

}
