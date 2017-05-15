
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : VehicleService.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月9日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.customer.RetainCustomersDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 车辆信息
 * 
 * @author zhanshiwei
 * @date 2016年8月9日
 */

public interface VehicleService {

    public PageInfoDto queryVehicleInfo(Map<String, String> queryParam) throws ServiceBizException;

    public Map<String, Object> queryVehicleInfoByid(Long id) throws ServiceBizException;

    public Long addVehicleInfo(RetainCustomersDTO tetainDto, String ownerNo) throws ServiceBizException;

    public void modifyVehicleInfo(Long id, RetainCustomersDTO tetainDto) throws ServiceBizException;

    public List<Map> queryVehicleInfoforExport(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto queryVehicleAndOwnerInfo(Map<String, String> queryParam) throws ServiceBizException;

    public List<Map> queryVehicleInfoByParams(String params) throws ServiceBizException;

    public PageInfoDto queryVehicleInfoForRepair(Map<String, String> queryParam) throws ServiceBizException;

}
