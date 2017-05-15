
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : RepairOrderPO.java
*
* @Author : ZhengHe
*
* @Date : 2016年8月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月10日    ZhengHe    1.0
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
* 工单Model
* @author ZhengHe
* @date 2016年8月10日
*/

@Table("tt_repair_order")
@CompositePK({"DEALER_CODE","RO_NO"})
public class RepairOrderPO extends BaseModel{

}
