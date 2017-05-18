
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : LossVehicleResultService.java
*
* @Author : sqh
*
* @Date : 2017年3月31日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月31日    sqh    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.vehicleLoss;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author sqh
* @date 2017年3月31日
*/

public interface LossVehicleResultService {
    /**
     * 查询客户流失报警回访结果
     * 
     * @param queryParam
     * @return
     * @throws ServiceBizException
     */
    public PageInfoDto queryLossVehicleResult(Map<String, String> queryParam) throws ServiceBizException;
    
    /**
     * 导出客户流失报警回访结果
     * 
     * @param queryParam
     * @return
     * @throws ServiceBizException
     */
    public List<Map> exportLossVehicleResult(Map<String, String> queryParam) throws ServiceBizException;
}
