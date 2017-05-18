
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerService.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月16日    zhanshiwei    1.0
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
 * 保有客户维护
 * 
 * @author zhanshiwei
 * @date 2016年8月16日
 */

public interface CustomerService {

    public PageInfoDto queryCustomerInnfo(Map<String, String> queryParam,String DealerCode) throws ServiceBizException;

    public Map<String, Object> queryCustomerVehicleInfoByid(long id) throws ServiceBizException;

    public Map<String, Object> modifyRetainCusVehicleInfo(long id, RetainCustomersDTO tetainDto,
                                                             String customerNo) throws ServiceBizException;

    public PageInfoDto queryCustomerSelectInfo(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> queryOwnerCusforExport(Map<String, String> queryParam) throws ServiceBizException;
}
