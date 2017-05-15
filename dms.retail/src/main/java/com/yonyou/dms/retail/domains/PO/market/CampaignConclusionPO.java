package com.yonyou.dms.retail.domains.PO.market;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 活动执行总结编辑PO
 * @author Benzc
 * @date 2017年2月24日
 */
@Table("TT_CAMPAIGN_CONCLUSION")
@CompositePK({ "CAMPAIGN_CODE", "DEALER_CODE" })
public class CampaignConclusionPO extends BaseModel{

}
