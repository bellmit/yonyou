
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerComplaintDetailPO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年7月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月28日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.domains.PO.customerManage;

import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 客户投诉主表PO
* @author zhanshiwei
* @date 2016年7月28日
*/
@Table("tt_customer_complaint_detail")
@IdName("COMPLAINT_DETAIL_ID")
@BelongsTo(parent = CustomerComplaintPO.class, foreignKeyName = "complaint_id")
public class CustomerComplaintDetailPO extends BaseModel{

}
