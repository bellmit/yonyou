
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : EmployeeListDto.java
*
* @Author : zhanshiwei
*
* @Date : 2016年12月31日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月31日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.domains.DTO.basedata;

import java.util.List;

/**
* 
* @author zhanshiwei
* @date 2016年12月31日
*/

public class EmployeeListDto {
    private List<EmployeeDto>       empList;

    
    public List<EmployeeDto> getEmpList() {
        return empList;
    }

    
    public void setEmpList(List<EmployeeDto> empList) {
        this.empList = empList;
    }
}
