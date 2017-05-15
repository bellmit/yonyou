
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BoAddItemPO.java
*
* @Author : jcsi
*
* @Date : 2016年10月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月14日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.PO.order;

import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.common.domains.PO.basedata.TtBookingOrderPO;
import com.yonyou.dms.framework.domain.BaseModel;

/**
* 预约单-附加项目
* @author jcsi
* @date 2016年10月14日
*/

@Table("TT_BO_ADD_ITEM")
@IdName("BO_ADD_ITEM_ID")
@BelongsTo(parent = TtBookingOrderPO.class, foreignKeyName = "BO_ID")
public class BoAddItemPO extends BaseModel{

}
