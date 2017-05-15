
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : StockInPO.java
*
* @Author : yll
*
* @Date : 2016年9月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月18日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.PO.stockmanage;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* 整车入库
* @author yll
* @date 2016年9月18日
*/
@Table("tt_vs_entry")
@IdName("ENTRY_ID")
public class StockInPO  extends BaseModel{

}
