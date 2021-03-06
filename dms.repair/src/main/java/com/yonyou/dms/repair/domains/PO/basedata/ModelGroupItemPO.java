
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : ModelGroupItemPO.java
*
* @Author : DuPengXin
*
* @Date : 2016年7月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月22日    DuPengXin   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.PO.basedata;

import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* @author DuPengXin
* @date 2016年7月22日
*/
@Table("tm_model_group_item")
@IdName("ITEM_ID")
@BelongsTo(parent = MaintainModelPO.class, foreignKeyName = "MODEL_GROUP_ID")
public class ModelGroupItemPO extends BaseModel{

}
