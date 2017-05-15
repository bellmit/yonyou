
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : LossVehicleTraceTaskService.java
*
* @Author : sqh
*
* @Date : 2017年3月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月29日    sqh    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.vehicleLoss;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author sqh
* @date 2017年3月29日
*/

public interface LossVehicleTraceTaskService {
    /**
     * 查询客户流失报警录入回访
     * 
     * @param queryParam
     * @return
     * @throws ServiceBizException
     */
    public PageInfoDto queryLossVehicleTraceTask(Map<String, String> queryParam) throws ServiceBizException;
    
    /**
     * 查询客户流失报警录入回访
     * 
     * @param queryParam
     * @return
     * @throws ServiceBizException
     */
    public List<Map> queryLossVehicleTraceTask1(Map<String, String> queryParam) throws ServiceBizException;
    
    /**
     * 通过vin查询客户流失报警录入回访
     * 
     * @param queryParam
     * @return
     * @throws ServiceBizException
     */
    public List<Map> queryLossVehicleTraceTaskByVin(@PathVariable(value = "vin") String vin) throws ServiceBizException;
    
	/**
	 * 导出客户流失报警录入回访
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> exportLossVehicleTraceTask(Map<String, String> queryParam) throws ServiceBizException;
	
	/**
	 * 查询DCRC专员
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	List<Map> querySales(Map<String, String> queryParam) throws ServiceBizException;
	
	/**
	 * 销售录入跟踪任务查询
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	List<Map> querySalesOrderTrackTask(Map<String, String> queryParam) throws ServiceBizException;
	
    /**
     * 查询流失修后跟踪记录
     * 
     * @param queryParam
     * @return
     * @throws ServiceBizException
     */
    public PageInfoDto QueryLossAlarmTraceTaskLog(@RequestParam Map<String, String> queryParam) throws ServiceBizException;
}
