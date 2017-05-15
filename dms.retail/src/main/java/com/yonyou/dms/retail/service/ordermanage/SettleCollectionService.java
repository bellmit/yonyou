
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : CustomerGatheringService.java
*
* @Author : xukl
*
* @Date : 2016年10月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月14日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.ordermanage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.ordermanage.CustomerGatheringDTO;
import com.yonyou.dms.retail.domains.DTO.ordermanage.GathringMaintainDTO;
import com.yonyou.dms.retail.domains.DTO.ordermanage.SettleCollectionWriteoffTagDTO;

/**
* 结算收款控制类
* @author xukl
* @date 2016年10月14日
*/
public interface SettleCollectionService {
    
    public List<Map> qryCustomer(String employeeNo,String dealerCode) throws ServiceBizException;
    
    public List<Map> qryCustomer2(String cusNo,String dealerCode) throws ServiceBizException;
    
    public List<Map> qrySupple(String vin,String license) throws ServiceBizException;
    
    public List<Map> qryReturn(String soNo,String dealerCode) throws ServiceBizException;
    
    public List<Map> queryreceiveNo(String soNo,String receiveNo) throws ServiceBizException;
    
    public List<Map> qrySales(String soNo,String dealerCode) throws ServiceBizException;

    public PageInfoDto qryCustomerGathering(Map<String, String> queryParam)throws ServiceBizException;
    
    public PageInfoDto qryCustomerGa(Map<String, String> queryParam,String cus)throws ServiceBizException;

    public Long addCustomerGathering(CustomerGatheringDTO customerGatheringDTO, String receiveNo) throws ServiceBizException;

    public  List<Map> addCustomerGatheringMain(GathringMaintainDTO customerGatheringDTO) throws ServiceBizException;
    
    public String addCustomerGatheringWriteoffTag(GathringMaintainDTO SettleCollectionWriteoffTagdto) throws ServiceBizException;

    public void editCustomerGathering(Long id, CustomerGatheringDTO customerGatheringDTO) throws ServiceBizException;

    public Map qryCustGathering(Long id) throws ServiceBizException;

	public PageInfoDto queryAllVehiclePayManage(Map<String, String> queryParam) throws ServiceBizException;

}
