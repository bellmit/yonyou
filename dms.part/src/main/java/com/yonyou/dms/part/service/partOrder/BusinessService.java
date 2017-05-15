
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.commonAS
*
* @File name : BusinessService.java
*
* @Author : 
*
* @Date : 2017年5月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月11日        1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.partOrder;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPartPeriodReportDTO;

/**
* 
* @author 
* @date 2017年5月11日
*/

public interface BusinessService {
    public  void updateRoManage(String entityCode,String roNO,long userId) throws ServiceBizException, Exception;

    public void updateRepairOrder(String dealerCode, String roNo);
    public int createOrUpdateDaily(TtPartPeriodReportDTO db, String entityCode)throws ServiceBizException, Exception;
}
