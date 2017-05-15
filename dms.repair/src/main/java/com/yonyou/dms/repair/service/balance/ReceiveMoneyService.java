
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : ReceiveMoneyService.java
*
* @Author : jcsi
*
* @Date : 2016年9月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月28日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.balance;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.balance.ChargeDerateDTO;
import com.yonyou.dms.repair.domains.DTO.balance.ReceiveMoneyDTO;

/**
*
* @author jcsi
* @date 2016年9月28日
*/

public interface ReceiveMoneyService {
    
    
    public Long saveReceiveMoney(ReceiveMoneyDTO receiveMoneyDto)throws ServiceBizException;
    
    public List<Map> findReceiveMoneyByPayobjId(Long id)throws ServiceBizException;
    
    public Long saveChargeDerate(ChargeDerateDTO chargeDerateDTO)throws ServiceBizException;
    
    public List<Map> findChargeDerate(Long id)throws ServiceBizException;
    
    
    public void updateWriteoffTag(Long id)throws ServiceBizException; 
    
  

}
