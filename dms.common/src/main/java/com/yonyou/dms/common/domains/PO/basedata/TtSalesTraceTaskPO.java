
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : PotentialCusPO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月1日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月1日    zhanshiwei    1.0
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
* 潜在客户PO
* @author zhanshiwei
* @date 2016年9月1日
*/
@Table("TT_SALES_TRACE_TASK")
@CompositePK({"DEALER_CODE","TRACE_TASK_ID"})
public class TtSalesTraceTaskPO extends BaseModel{

}
