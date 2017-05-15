
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TtEsCustomerOrderPO.java
*
* @Author : LiGaoqi
*
* @Date : 2017年2月20日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月20日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;


/**
* TODO description
* @author LiGaoqi
* @date 2017年2月20日
*/
@Table("tt_es_customer_order")
@CompositePK({"DEALER_CODE","ITEM_ID","EC_ORDER_NO"})
public class TtEsCustomerOrderPO extends BaseModel {

}
