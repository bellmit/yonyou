
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : VsStockLog.java
*
* @Author : yangjie
*
* @Date : 2017年1月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月9日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* TODO 整车库存-操作日志
* @author yangjie
* @date 2017年1月9日
*/

@Table("TT_VS_STOCK_LOG")
@IdName("ITEM_ID")
public class VsStockLogPO extends BaseModel {

}
