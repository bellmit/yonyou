
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : VsTransferItemPO.java
*
* @Author : yangjie
*
* @Date : 2017年1月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月8日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.vehicle.domains.PO.stockManage;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* TODO 整车库存-移库单明细 
* @author yangjie
* @date 2017年1月8日
*/

@Table("TT_VS_TRANSFER_ITEM")
@CompositePK({"DEALER_CODE","ST_NO","VIN"})
public class VsTransferItemPO extends BaseModel{

}
