
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : RepairTypePO.java
*
* @Author : DuPengXin
*
* @Date : 2016年7月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月27日    DuPengXin   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 维修类型
* @author administrator
* 
*/
@Table("TM_REPAIR_TYPE")
@CompositePK({"DEALER_CODE","REPAIR_TYPE_CODE"})
public class TmRepairTypePO extends BaseModel {

}
