
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : balanceRepartServiceImpl.java
*
* @Author : ZhengHe
*
* @Date : 2016年10月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月26日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.report.service.impl;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 结算Servcie
* @author ZhengHe
* @date 2016年10月26日
*/

public interface balanceRepartService{

    public PageInfoDto queryBalance(Map<String, String> queryParam) throws ServiceBizException ;
    
}
