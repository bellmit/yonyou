/**
 * 
 */
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @author Administrator
 *
 */
@Table("tt_campaign_plan")
@CompositePK({"DEALER_CODE","CAMPAIGN_CODE"})
public class TtCampaignPlanPO extends BaseModel{

}
