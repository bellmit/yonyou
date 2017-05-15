
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BookingOrderService.java
*
* @Author : jcsi
*
* @Date : 2016年10月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月14日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.order;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.order.BookingOrderDTO;
import com.yonyou.dms.repair.domains.DTO.order.DrivingServiceOrderDTO;

/**
*
* @author jcsi
* @date 2016年10月14日
*/

public interface BookingOrderService {

    
    public PageInfoDto search(Map<String,String>  queryParam)throws ServiceBizException;
    
    public Long addBookingOrder(BookingOrderDTO bookingOrderDto)throws ServiceBizException;
    
    public void editBookingOrder(Long id,BookingOrderDTO bookingOrderDto)throws ServiceBizException;
    
    public void addDrivingServiceOrder(DrivingServiceOrderDTO drivingServiceOrderDto)throws ServiceBizException;
    
    public Map searchBookingOrderByBoId(Long id)throws ServiceBizException;
    
    public List<Map> searchBoLabourByBoId(Long id)throws ServiceBizException;
    
    public List<Map> searchBoPartByBoId(Long id)throws ServiceBizException;
    
    public List<Map> searchAddItemByBoId(Long id)throws ServiceBizException;
    
    public void updateBookingOrderStatusByBoId(Long id)throws ServiceBizException;
    
    public void deleteByBoId(Long id)throws ServiceBizException;
    
    public List<Map> searchOrderLabourByBoId(Long id)throws ServiceBizException;
    
    public List<Map> searchOrderPartByBoId(Long id)throws ServiceBizException;
    
    public Map  searchCarIfoByBoId(Long id)throws ServiceBizException;
}
