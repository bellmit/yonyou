
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : VehicleInspectService.java
*
* @Author : zhanshiwei
*
* @Date : 2016年12月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月6日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.service.stockManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.VehicleInspectDTO;

/**
 * 车辆验收
 * 
 * @author zhanshiwei
 * @date 2016年12月6日
 */

public interface VehicleInspectService {

    public PageInfoDto queryVehicleInspect(Map<String, String> queryParam) throws ServiceBizException;

    public Map<String, Object> queryVehicleInspectById(Long id) throws ServiceBizException;

    public List<Map> queryVinspectionMar(Long id) throws ServiceBizException;

    public List<Map> queryPdiInspection(int inspectType, String bussinessNo) throws ServiceBizException;

    public void modifyVehicleInspect(Long id, VehicleInspectDTO veInspDto) throws ServiceBizException;

    public void modifyVehicleInspectFinishSel(VehicleInspectDTO veInspDto) throws ServiceBizException;

    public List<Map> exportVehicleInspectInfo(Map<String, String> queryParam) throws ServiceBizException;

}
