
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SOTDMS007.java
*
* @Author : Administrator
*
* @Date : 2017年3月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月8日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiAppNCultivateDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author Administrator
* @date 2017年3月8日
*/

public interface SOTDMS007Coud {
    public int getSOTDMS007(List<TiAppNCultivateDto> dtList) throws ServiceBizException;
}
