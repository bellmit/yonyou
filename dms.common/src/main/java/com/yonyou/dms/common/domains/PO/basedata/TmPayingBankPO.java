
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TmPayingBankPO.java
*
* @Author : LiGaoqi
*
* @Date : 2017年5月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月12日    LiGaoqi    1.0
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
* @author LiGaoqi
* @date 2017年5月12日
*/
@Table("tm_paying_bank")
@CompositePK({"DEALER_CODE","BANK_CODE"})
public class TmPayingBankPO extends BaseModel {

}
