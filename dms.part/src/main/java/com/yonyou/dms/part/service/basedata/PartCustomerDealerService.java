
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartCustomerDealerService.java
*
* @Author : chenwei
*
* @Date : 2017年3月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月29日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.PartCostAdjustDto;

/**
* 业务往来客户经销商 接口
* @author chenwei
* @date 2017年2月29日
*/

public interface PartCustomerDealerService {

    public List<Map> queryPartCustomerDealerList(Map<String, String> queryParam) throws ServiceBizException;
    
}
