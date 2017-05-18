
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerResoPO.java
*
* @Author : zhengcong
*
* @Date : 2017年3月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
 * 1. 2017年3月22日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* 业务往来客户PO
* @Author : zhengcong
*
* @Date : 2017年3月22日
*/
@Table("TM_CUSTOMER_TYPE")
@CompositePK({"DEALER_CODE","CUSTOMER_TYPE_CODE"})
public class CustomerResoPO extends BaseModel{

}
