
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SADMS114.java
*
* @Author : LiGaoqi
*
* @Date : 2017年5月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月12日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.PayingBankDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author LiGaoqi
* @date 2017年5月12日
*/

public interface SADMS114 {
    public int getSSADMS114(String dealerCode,List<PayingBankDTO> list) throws ServiceBizException;
}
