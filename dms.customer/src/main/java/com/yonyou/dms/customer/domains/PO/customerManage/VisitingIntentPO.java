
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : VisitingIntentPO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月31日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月31日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.domains.PO.customerManage;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* 展厅客户意向报价
* @author zhanshiwei
* @date 2016年8月31日
*/
@Table("tt_visiting_intent_detail")
@IdName("VISITING_INTENT_ID")
//@BelongsTo(parent = VisitingRecordPO.class, foreignKeyName = "ITEM_ID")
public class VisitingIntentPO extends BaseModel{

}
