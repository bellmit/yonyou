
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SoAuditingPO.java
*
* @Author : xukl
*
* @Date : 2016年9月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月28日    xukl    1.0
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
* 销售订单-审批记录
* @author xukl
* @date 2016年9月28日
*/
@Table("TT_SO_AUDITING")
@IdName("ITEM_ID")
public class SoAuditingPO  extends BaseModel{

}
