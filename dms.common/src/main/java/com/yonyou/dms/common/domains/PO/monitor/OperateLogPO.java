
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : OperateLog.java
 *
 * @Author : yll
 *
 * @Date : 2016年7月14日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月14日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.common.domains.PO.monitor;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 操作日志模型
 * @author LGQ
 * @date 2017年1月4日
 */
@Table("TT_OPERATE_LOG")
@IdName("LOG_ID")
public class OperateLogPO extends BaseModel{

}
