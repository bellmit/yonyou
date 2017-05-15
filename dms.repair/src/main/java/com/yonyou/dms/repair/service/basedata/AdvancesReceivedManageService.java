
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : AdvancesReceivedManageService.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月8日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月8日
*/

public interface AdvancesReceivedManageService {
    
    public PageInfoDto retrieveHasPrePay() throws ServiceBizException;
    
    public List<Map> retrieveHasPrePayExport() throws ServiceBizException;
    
    public PageInfoDto retrieveByCustomer(Map<String, String> map) throws ServiceBizException;
    
    public PageInfoDto retrieveInsuranceFeeByCustomer(Map<String, String> map) throws ServiceBizException;
    
    public PageInfoDto queryByNoOrSpell(Map<String, String> map) throws ServiceBizException;
    
    public String inster(Map map) throws ServiceBizException;
   
}
