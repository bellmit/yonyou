
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.commonSales
*
* @File name : OrgDeptService.java
*
* @Author : zhanshiwei
*
* @Date : 2016年12月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月9日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.commonSales.service.basedata;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 组织
* @author zhanshiwei
* @date 2016年12月9日
*/

public interface OrgDeptService {
    public Long getOrganizationIdByEmployeeNo(String employeeNo) throws ServiceBizException;
}
