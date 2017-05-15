
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : SalesPartItem.java
*
* @Author : jcsi
*
* @Date : 2016年8月4日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月4日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.commonAS.domains.PO.basedata;

import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* 销售出库明细
* @author jcsi
* @date 2016年8月4日
*/
@Table("tt_part_sales_item")
@IdName("ITEM_ID")
@BelongsTo(parent = PartSalesPo.class, foreignKeyName = "SALES_PART_ID")
public class PartSalesItemPo extends BaseModel{

}
