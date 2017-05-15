
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : DecorationPartPO.java
*
* @Author : zsw
*
* @Date : 2016年9月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月5日    zsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.domains.PO.ordermanage;

import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* 装潢操作项配件
* @author zsw
* @date 2016年9月5日
*/
@Table("tm_decrodate_part")
@IdName("DECRODATE_PART_ID")
@BelongsTo(parent = DecorationPO.class, foreignKeyName = "DECRODATE_ID")
public class DecorationPartPO extends BaseModel{

}
