
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CusApproveService.java
*
* @Author : Administrator
*
* @Date : 2017年1月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月5日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.PotentialCusDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO 客户休眠申请审批
* @author Administrator
* @date 2017年1月5日
*/

public interface CusApproveService {
    
    public PageInfoDto queryApproveCusInfo(Map<String, String> queryParam) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryApproveCusInfoByid(String id) throws ServiceBizException;
    
    public void modifyPotentialCusInfo(String id, PotentialCusDTO potentialCusDto) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryPotentialCusforExport(Map<String, String> queryParam) throws ServiceBizException;
    
}
