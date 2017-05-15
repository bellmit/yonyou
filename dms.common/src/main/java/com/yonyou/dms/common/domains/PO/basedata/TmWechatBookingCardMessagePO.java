package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;


@Table("TM_WECHAT_BOOKING_CARD_MESSAGE")
@CompositePK({"DEALER_CODE","ID","RESERVE_ID"})
public class TmWechatBookingCardMessagePO extends BaseModel {

}
