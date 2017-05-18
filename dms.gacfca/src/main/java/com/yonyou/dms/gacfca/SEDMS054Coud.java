
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SEDMS054.java
*
* @Author : LiGaoqi
*
* @Date : 2017年4月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月19日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.SEDMS054DTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author LiGaoqi
* @date 2017年4月19日
*/

public interface SEDMS054Coud {
    public int getSEDMS054(LinkedList<SEDMS054DTO> voList,String dealerCode) throws ServiceBizException;

}
