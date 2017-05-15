
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TtAlertMsgMappingPO.java
*
* @Author : LiGaoqi
*
* @Date : 2017年1月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月17日    LiGaoqi    1.0
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
* @date 2017年1月17日
*/
@Table("tt_alert_msg_mapping")
@CompositePK({"DEALER_CODE","USER_ID","MSG_ID"})
public class TtAlertMsgMappingPO extends BaseModel {

}
