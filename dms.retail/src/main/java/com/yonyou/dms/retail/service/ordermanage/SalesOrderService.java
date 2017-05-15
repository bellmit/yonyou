
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.retail
 *
 * @File name : DecroDateService.java
 *
 * @Author : xukl
 *
 * @Date : 2016年9月5日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月5日    xukl    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.retail.service.ordermanage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.SalesReturnDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO;

/**
 * SalesOrderService
 * 
 * @author xukl
 * @date 2016年9月5日
 */
@SuppressWarnings("rawtypes")
public interface SalesOrderService {
    
    public List<Map> querysoNo(String soNo,Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto qrySalesOrders(Map<String, String> queryParam) throws ServiceBizException;
    
    public List<Map> commidAudit(Map<String, String> queryParam) throws ServiceBizException;
    
    public List<Map> qrySalesOrdersDetial(Map<String, String> queryParam,String oldSoNo) throws ServiceBizException;

    public Map getSalesOrderById(String id) throws ServiceBizException;

    public void updateSalesOrder(String fIsChanged, SalesReturnDTO salesOrderDTO) throws ServiceBizException;
    
    public Map addSalesOrdersSubmit(SalesReturnDTO salesReturnDTO) throws ServiceBizException;
    
    public Map addSalesOrders(SalesReturnDTO salesReturnDTO, String soNo,String fIsChanged) throws ServiceBizException;

    public void submitSalesOrder(Long id, SalesOrderDTO salesOrderDTO,
                                 List<BasicParametersDTO> basiDtolist) throws ServiceBizException;
    
    public void submitAndCheckVerified(SalesOrderDTO salesOrderDTO) throws ServiceBizException;
    
    public Map saveCommitAudit(SalesOrderDTO salesOrderDTO) throws ServiceBizException;
    
    public List<Map> qryDecorationProject(Long id) throws ServiceBizException;

    public List<Map> qryServiceProject(Long id) throws ServiceBizException;

    public List<Map> qryDeracotionMaterial(Long id) throws ServiceBizException;

    public void cancelSalesOrder(String soNo);

    public Map addSellBack(SalesOrderDTO salesOrderDTO, String soNo) throws ServiceBizException;

    public void updateSellBack(Long id, SalesOrderDTO salesOrderDTO) throws ServiceBizException;

    public void submitSellBack(Long id, SalesOrderDTO salesOrderDTO,
                               List<BasicParametersDTO> basiDtolist) throws ServiceBizException;

    public PageInfoDto slctSalesOrders(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto qryComplaintSalesOrders(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto qrySRSForMangAudit(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto qrySRSForFunction(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto qrySRSForFincAudit(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto queryPoCusWholesaleInfo(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto getIntentSalesOrder(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto queryMatchVehicleByCodeDetail(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto queryCustomerAndIntent(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto queryCustomerAndIntent1(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto qrySRSForSellBack(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto qrySRSForSBKSlt(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto querySubmitSalesHis(Map<String, String> queryParam,String soNo) throws ServiceBizException;
    
    public PageInfoDto qrySRSForSettCo(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto qrySRSForInvoice(Map<String, String> queryParam) throws ServiceBizException;
    
    public Map getPritSalesOrderById(Long id) throws ServiceBizException;
    
    public PageInfoDto queryoldCustomerVin(String id) throws ServiceBizException;
    
    public PageInfoDto queryallLoanRate(String id) throws ServiceBizException;
    
    public Map addSalesOrder(SalesOrderDTO salesOrderDTO, String soNo) throws ServiceBizException;
    
    public List<Map> checktmPoTentialCustomer(String id) throws ServiceBizException;
    
    public String checksaveEcOrder(String id) throws ServiceBizException;
    
    /*public List<Map> commidAudit(String id) throws ServiceBizException;*/
    
    public PageInfoDto qrySRSForInvoiceSet(Map<String, String> queryParam) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
   	public List<Map> queryMenuAction() throws ServiceBizException;//销售退回作废权限


}
