
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SEDMS061.java
*
* @Author : LiGaoqi
*
* @Date : 2017年5月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月8日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author LiGaoqi
* @date 2017年5月8日
*/

public interface SEDMS061 {
    public int getSEDMS061(String soNo) throws ServiceBizException;

}
