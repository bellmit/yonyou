/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : WorkerTypePo.java
*
* @Author : jcsi
*
* @Date : 2016年7月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月8日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/


package com.yonyou.dms.repair.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* 工种定义
* @author jcsi
* @date 2016年7月29日
 */
@Table("tm_worker_type")
@CompositePK({"DEALER_CODE","WORKER_TYPE_CODE"})
public class WorkerTypePo extends BaseModel{

}
