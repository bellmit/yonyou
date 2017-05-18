
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SADMS061.java
*
* @Author : LiGaoqi
*
* @Date : 2017年4月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月18日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.SADCS060Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author LiGaoqi
* @date 2017年4月18日
*/

public interface SADMS061Coud {
    public LinkedList<SADCS060Dto> getSADMS061(String soNo,String vin,String isConfirmed) throws ServiceBizException;

}
