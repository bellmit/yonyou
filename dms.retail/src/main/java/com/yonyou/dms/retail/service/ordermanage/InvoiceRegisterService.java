
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : InvoiceRegisterService.java
*
* @Author : DuPengXin
*
* @Date : 2016年9月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月28日    DuPengXin   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.ordermanage;

import java.util.List;

import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.InvoiceRefundDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.ordermanage.InvoiceRegisterDTO;

/**
* 开票登记接口
* @author DuPengXin
* @date 2016年9月28日
*/

public interface InvoiceRegisterService {
    public PageInfoDto queryInvoiceRegister(Map<String, String> queryParam) throws ServiceBizException;
    public PageInfoDto qrySRSForInvoiceSet(Map<String, String> queryParam) throws ServiceBizException;
    public PageInfoDto qrySRSForInvoiceSetrefund(Map<String, String> queryParam) throws ServiceBizException;
    public String addInvoiceRegister(InvoiceRegisterDTO irdto) throws ServiceBizException;
    public String addInvoiceRefund(InvoiceRefundDTO irdto) throws ServiceBizException;
    public void updateInvoiceRegister(Long id,InvoiceRegisterDTO irdto) throws ServiceBizException;
    public void updateInvoiceRegisterInvoice(Long id,InvoiceRegisterDTO irdto) throws ServiceBizException;
    public Map findById(Long id) throws ServiceBizException;
    public Map qrySalesOrderRegisterWx(Map<String, String> queryParam) throws ServiceBizException;
    public void cancelInvoiceRegister(Long id)throws ServiceBizException;
    List<Map> queryRetainCustrackforExport(Map<String, String> queryParam) throws ServiceBizException;//使用excel导出
    
    public String searchVehicleSum(String m,String c) throws ServiceBizException;
}
