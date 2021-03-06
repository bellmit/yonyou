
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartBuyItemPO.java
*
* @Author : xukl
*
* @Date : 2016年8月3日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月3日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.PO.stockmanage;

import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* PartBuyItemPO
* @author xukl
* @date 2016年8月3日
*/
@Table("TT_PART_BUY_ITEM")
@IdName("ITEM_ID")
@BelongsTo(parent = PartBuyPO.class, foreignKeyName = "PART_BUY_ID")
public class PartBuyItemPO extends BaseModel{

}
