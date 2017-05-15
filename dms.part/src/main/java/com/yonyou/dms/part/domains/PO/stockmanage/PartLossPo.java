
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartLossPo.java
*
* @Author : jcsi
*
* @Date : 2016年8月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月13日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.PO.stockmanage;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* 配件报损
* @author jcsi
* @date 2016年8月13日
*/

@Table("TT_PART_LOSS")
@IdName("PART_LOSS_ID")
public class PartLossPo extends BaseModel{

}
