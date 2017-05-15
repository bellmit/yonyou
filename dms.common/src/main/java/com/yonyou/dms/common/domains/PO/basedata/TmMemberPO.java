
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TmMemberPO.java
*
* @Author : LiGaoqi
*
* @Date : 2017年2月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月22日    LiGaoqi    1.0
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
* @date 2017年2月22日
*/
@Table("tm_member")
@CompositePK({"MEMBER_NO","DEALER_CODE"})
public class TmMemberPO extends BaseModel {

}
