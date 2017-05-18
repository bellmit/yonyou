
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerResoService.java
*
* @Author : zhengcong
*
* @Date : 2017年3月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月22日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.customer.domains.DTO.basedata.BusinessCustomerDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;


/**
* 业务往来客户资料
* @author chenwei
* @date 2017年3月28日
*/

public interface BusinessCustomerService {
    public PageInfoDto queryBusinessCustomer(Map<String,String> queryParam) throws ServiceBizException; //根据条件查询
    Map<String, String> findByCustomerCode(String customerCode) throws ServiceBizException;//根据customerCode查询详细信息
    public void modifyByCustomerCode(String CustomerCode,BusinessCustomerDTO cudto) throws ServiceBizException;///根据customerCode修改
    void addBusinessCustomer(BusinessCustomerDTO cudto)throws ServiceBizException;///新增
    public List<Map> queryCustomersDict(Map<String, String> queryParam) throws ServiceBizException;//查询客户类型下拉框
    public List<Map> queryCustomerRecordforExport(Map<String, String> queryParam) throws ServiceBizException;//查询业务来往客户经销商excel数据
}
