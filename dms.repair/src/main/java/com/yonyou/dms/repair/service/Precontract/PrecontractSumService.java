
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : PrecontractSumService.java
*
* @Author : Administrator
*
* @Date : 2017年4月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月6日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.Precontract;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO 预约汇总
* @author zhl
* @date 2017年4月6日
*/

public interface PrecontractSumService {
    
    public  PageInfoDto QueryPrecontractSum(Map<String, String> queryParam)throws ServiceBizException;

}
