
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartAllocateOutItemPo.java
*
* @Author : jcsi
*
* @Date : 2016年7月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月27日    Administrator    1.0
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
* 调拨出库明细
* @author jcsi
* @date 2016年7月27日
*/
@Table("TT_PART_ALLOCATE_OUT_ITEM")
@IdName("ITEM_ID")
@BelongsTo(parent = PartAllocateOutPo.class, foreignKeyName = "ALLOCATE_OUT_ID")
public class PartAllocateOutItemPo extends BaseModel{

}
