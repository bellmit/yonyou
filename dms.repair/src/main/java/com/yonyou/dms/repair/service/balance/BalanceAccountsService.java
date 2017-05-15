
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BalanceAccountsService.java
*
* @Author : ZhengHe
*
* @Date : 2016年9月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月27日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.balance;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.balance.BalanceAccountsDTO;

/**
* 结算单
* @author ZhengHe
* @date 2016年9月27日
*/

public interface BalanceAccountsService {

    public Long addBalanceAccounts(BalanceAccountsDTO baDto)throws ServiceBizException;
    
    public PageInfoDto search(Map<String,String> param);
    
    public List<Map> findBalancePayobjById(Long id);
    
    public PageInfoDto queryCustomerOrVehicle(Map<String,String> param)throws ServiceBizException;
    
    public List<Map> excelReceiveMoney(Map<String,String> param)throws ServiceBizException;
    
    public PageInfoDto queryBalanceAccounts(Map<String,String> param,String ...params)throws ServiceBizException;
    
    public Long cancelBalanceAccounts(BalanceAccountsDTO baDto)throws ServiceBizException;
    
    public Map selectBalancePayobj(Long id,String paymentObjectCode)throws ServiceBizException;
    
    public Map findBalanceAmountsById(Long id)throws ServiceBizException;
    
    public List<Map> queryBalanceLabour(Long id)throws ServiceBizException;
    
    public List<Map> queryBalanceRepairPart(Long id)throws ServiceBizException;
    
    public List<Map> queryBalanceAddItem(Long id)throws ServiceBizException;
    
    public List<Map> queryBalanceSalesPart(Long id)throws ServiceBizException;
    
    public List<Map> queryBalancePayobj(Long id)throws ServiceBizException;
    
    public List<Map> queryBalanceAllocatePartById(Long id)throws ServiceBizException;
    
    public Map queryBalanceSalesById(Long id)throws ServiceBizException;
    
    public Map queryBalanceAllocateById(Long id)throws ServiceBizException;
    
}
