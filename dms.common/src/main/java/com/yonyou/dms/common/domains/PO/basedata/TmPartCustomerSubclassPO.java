
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TmPartCustomerSubclassPO.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月9日    dingchaoyu    1.0
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
* TODO description
* @author dingchaoyu
* @date 2017年5月9日
*/
@Table("TM_PART_CUSTOMER_SUBCLASS")
@CompositePK({"CUSTOMER_CODE","DEALER_CODE","MAIN_ENTITY"})
public class TmPartCustomerSubclassPO extends BaseModel{

}
