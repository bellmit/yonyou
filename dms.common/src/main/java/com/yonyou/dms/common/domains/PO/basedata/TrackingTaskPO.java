
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TrackingTaskPO.java
*
* @Author : LiGaoqi
*
* @Date : 2016年12月30日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月30日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;


/**
* TODO description
* @author LiGaoqi
* @date 2016年12月30日
*/
@Table("tm_tracking_task")
@IdName("TASK_ID")
public class TrackingTaskPO extends BaseModel {

}
