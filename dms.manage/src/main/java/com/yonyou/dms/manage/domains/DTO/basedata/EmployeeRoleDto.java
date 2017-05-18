
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : EmployeeRoleDto.java
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
	
package com.yonyou.dms.manage.domains.DTO.basedata;



/**
 * 员工角色信息
* @author jcsi
* @date 2016年7月8日
*/

public class EmployeeRoleDto {

    private Long employeeId;  
    
    private Integer role;  //角色

    
    public Long getEmployeeId() {
        return employeeId;
    }

    
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    
    public Integer getRole() {
        return role;
    }

    
    public void setRole(Integer role) {
        this.role = role;
    }
    
    
    
}
