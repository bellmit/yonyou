
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartInventoryItemPO.java
*
* @Author : ZhengHe
*
* @Date : 2016年7月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月26日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.PO.basedata;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* 配件盘点单明细Po
* @author ZhengHe
* @date 2016年7月26日
*/
@Table("tt_part_inventory_item")
@IdName("ITEM_ID")
//@BelongsTo(parent = PartInventoryPO.class, foreignKeyName = "PART_INVENTORY_ID")
public class PartInventoryItemPO extends BaseModel{

}
