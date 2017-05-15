
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SoDecrodatePO.java
*
* @Author : xukl
*
* @Date : 2016年9月23日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月23日    xukl    1.0
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

import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.domain.BaseModel;

/**
* 销售订单装潢项目明细
* @author xukl
* @date 2016年9月23日
*/
@Table("TT_SO_DECRODATE")
@IdName("SO_DECRODATE_ID")
@BelongsTo(parent = SalesOrderPO.class, foreignKeyName = "SO_NO_ID")
public class SoDecrodatePO extends BaseModel{

}
