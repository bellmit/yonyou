
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BookingLimitService.java
*
* @Author : zhanshiwei
*
* @Date : 2016年10月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月12日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.repair.service.basedata;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.BookingLimitDTO;
import com.yonyou.dms.repair.domains.PO.basedata.BookingLimitPO;

/**
 * 预约限量设置
 * 
 * @author zhanshiwei
 * @date 2016年10月12日
 */

public interface BookingLimitService {

    public PageInfoDto queryBookingLimit(Map<String, String> queryParam) throws ServiceBizException;

    public BookingLimitPO findBookingLimitById(Long id) throws ServiceBizException;

    public String addBookingLimit(BookingLimitDTO limidto) throws ServiceBizException;

    public void modifyBookingLimit(Long id, BookingLimitDTO lomidto) throws ServiceBizException;

    public void deleteBookingLimit(Long id) throws ServiceBizException;

}
