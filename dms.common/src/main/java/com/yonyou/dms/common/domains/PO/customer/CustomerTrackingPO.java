
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerTrackingPO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月22日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.PO.customer;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* 客户跟进PO
* @author zhanshiwei
* @date 2016年8月22日
*/
@Table("tt_customer_tracking")
@IdName("TRACKING_ID")
public class CustomerTrackingPO extends BaseModel{

}
