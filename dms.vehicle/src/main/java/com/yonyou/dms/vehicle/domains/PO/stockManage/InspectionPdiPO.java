
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : InspectionPdiPO.java
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
	
package com.yonyou.dms.vehicle.domains.PO.stockManage;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * pdi检查
 * 
 * @author zhanshiwei
 * @date 2016年12月8日
 */
@Table("tt_inspect_pdi")
@IdName("INSPECT_PDI_ID")
public class InspectionPdiPO extends BaseModel{

}
