package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 会员卡活动
* TODO description
* @author chenwei
* @date 2017年4月25日
 */
@Table("TM_MEMBER_CARD_ACTIVITY")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TmMemberCardActivityPO extends BaseModel{

}
