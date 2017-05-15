
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : ConfirmcarService.java
*
* @Author : zhongsw
*
* @Date : 2016年9月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月27日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.ordermanage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.ConfirmCarAndUpdateCustomerDTO;
import com.yonyou.dms.common.domains.DTO.stockmanage.VehicleDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO;

/**
* 交车确认方法
* @author zhongsw
* @date 2016年9月27日
*/

public interface ConfirmcarService {

    public PageInfoDto searchConfirmcar(Map<String, String> queryParam) throws ServiceBizException;
    
    public void updateVIN(Long id, SalesOrderDTO salesOrderDTO) throws ServiceBizException;
    
    public void updateCar(Long id,VehicleDTO vehicleDTO) throws ServiceBizException;
    
    public List<Map> quickQuery() throws ServiceBizException;
    
    public List<Map> searchTest(String id) throws ServiceBizException;
    
    public void updateCustomerInfo(ConfirmCarAndUpdateCustomerDTO ConfirmCarDTO) throws ServiceBizException;
    
    public void addSalesOrder(SalesOrderDTO salesOrderDTO) throws ServiceBizException;
    
    public void saveCustomerInfo(ConfirmCarAndUpdateCustomerDTO ConfirmCarDTO) throws ServiceBizException;
    
    public List<Map> searchConfirm(String id) throws ServiceBizException;
    
    public List<Map> searchPrint(String id) throws ServiceBizException;
}
