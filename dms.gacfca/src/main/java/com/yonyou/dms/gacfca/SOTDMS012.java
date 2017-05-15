
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SOTDMS012.java
*
* @Author : Administrator
*
* @Date : 2017年3月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月7日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiAppUCustomerInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author Administrator
* @date 2017年3月7日
*/

public interface SOTDMS012 {
    public int getSOTDMS012(List<TiAppUCustomerInfoDto> dtList) throws ServiceBizException;
}
