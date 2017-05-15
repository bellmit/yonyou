
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : VinspectionMarPO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年12月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月8日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.PO.stockmanage;

import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* 质损明细
* @author zhanshiwei
* @date 2016年12月8日
*/
@Table("tt_vinspection_mar")
@BelongsTo(parent = VehicleInspectPO.class, foreignKeyName = "VEHICLE_INSPECT_ID")
public class VinspectionMarPO extends BaseModel{

}
