
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartFlowPO.java
*
* @Author : xukl
*
* @Date : 2016年8月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月9日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.commonAS.domains.PO.basedata;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* PartFlowPO
* @author xukl
* @date 2016年8月9日
*/
@Table("tt_part_flow")
@IdName("FLOW_ID")
public class PartFlowPO extends BaseModel{

}
