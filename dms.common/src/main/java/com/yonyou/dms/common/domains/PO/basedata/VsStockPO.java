
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : VsStockPO.java
*
* @Author : yangjie
*
* @Date : 2016年12月31日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月31日    yangjie    1.0
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
* TODO 整车库存-车辆库存PO对象
* @author yangjie
* @date 2016年12月31日
*/

@Table("TM_VS_STOCK")
@CompositePK({"DEALER_CODE","VIN"})
public class VsStockPO extends BaseModel {

}
