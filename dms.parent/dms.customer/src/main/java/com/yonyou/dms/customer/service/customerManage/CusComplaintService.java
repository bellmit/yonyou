
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CusComplaintService.java
*
* @Author : Administrator
*
* @Date : 2016年7月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月27日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.customer.domains.DTO.customerManage.CustomerComplaintDTO;
import com.yonyou.dms.customer.domains.PO.customerManage.CustomerComplaintPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 客户投诉
 * 
 * @author zhanshiwei
 * @date 2016年7月27日
 */

public interface CusComplaintService {

    public PageInfoDto queryCusComplaint(Map<String, String> queryParam) throws ServiceBizException;

    public Long addCustomerComplaint(CustomerComplaintDTO complDTO, String complaintNo) throws ServiceBizException;

    public CustomerComplaintPO getCustomerComplaintById(Long id) throws ServiceBizException;

    public List<Map> queryComplaintDetail(Long id) throws ServiceBizException;

    public void modifyCustomerComplaint(Long id, CustomerComplaintDTO complDTO) throws ServiceBizException;

    public CustomerComplaintPO modifySettleSate(Long id, CustomerComplaintDTO complDTO) throws ServiceBizException;

    public CustomerComplaintPO complaintSettleforOem(Long id) throws ServiceBizException;

    public Map<String, Object> getCustomerComplaintOpeType() throws ServiceBizException;

    public Map<String, Object> queryComplainCounts() throws ServiceBizException;
    public Map<String, Object> getCustomerComplaintOrgCode(Long id) throws ServiceBizException;

}
