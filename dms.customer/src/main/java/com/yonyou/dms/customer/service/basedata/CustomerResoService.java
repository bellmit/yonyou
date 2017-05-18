
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

import java.util.Map;

import com.yonyou.dms.customer.domains.DTO.basedata.CustomerResoDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;


/**
* 业务往来客户
* @author zhanshiwei
* @date 2016年7月11日
*/

public interface CustomerResoService {
    public PageInfoDto queryContCustomer(Map<String,String> queryParam) throws ServiceBizException; //根据条件查询
    Map<String, String> findByCustomerTypeCode(String CUSTOMER_TYPE_CODE) throws ServiceBizException;//根据CUSTOMER_TYPE_CODE查询详细信息
    public void modifyByCustomerTypeCode(String CUSTOMER_TYPE_CODE,CustomerResoDTO cudto) throws ServiceBizException;///根据CUSTOMER_TYPE_CODE修改
    void addCustomerReso(CustomerResoDTO cudto)throws ServiceBizException;///新增

}
