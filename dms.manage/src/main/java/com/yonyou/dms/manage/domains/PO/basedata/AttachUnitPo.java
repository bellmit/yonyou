
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : AttachUnitPo.java
*
* @Author : Administrator
*
* @Date : 2016年12月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月16日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.domains.PO.basedata;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 车辆信息挂靠单位model
* 
* @author Administrator
* @date 2016年12月16日
*/


@Table("tm_attachunit")
@IdName("ATTACHUNIT_ID")
public class AttachUnitPo extends BaseModel {

}
