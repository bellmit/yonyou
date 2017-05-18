
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : EmployeeRolePo.java
*
* @Author : jcsi
*
* @Date : 2016年7月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月8日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.domains.PO.basedata;

import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.common.domains.PO.basedata.EmployeePo;
import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 员工角色信息
* @author jcsi
* @date 2016年7月8日
*/

@Table("tm_employee_role")
@IdName("EMPLOYEE_ROLE_ID")
@BelongsTo(parent = EmployeePo.class, foreignKeyName = "EMPLOYEE_ID")
public class EmployeeRolePo extends BaseModel{

}
