
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TtSalesCrPO.java
*
* @Author : Administrator
*
* @Date : 2017年1月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月10日    Administrator    1.0
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
* @author Administrator
* @date 2017年1月10日
*/
@Table("tt_sales_cr")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtSalesCrPO extends BaseModel{

}
