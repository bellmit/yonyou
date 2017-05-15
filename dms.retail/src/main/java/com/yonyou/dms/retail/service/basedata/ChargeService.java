
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : ChargeService.java
*
* @Author : Administrator
*
* @Date : 2016年7月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月10日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.basedata.ChargeDTO;
import com.yonyou.dms.retail.domains.PO.basedata.ChargePo;

/**
* TODO description
* @author zhongshiwei
* @date 2016年7月10日
*/

@SuppressWarnings("rawtypes")
public interface ChargeService {
    
    public ChargePo findById(String id) throws ServiceBizException;
    
    public PageInfoDto ChargeSQL(Map<String, String> queryParam) throws ServiceBizException;///查询
    
    public String insertCharge(ChargeDTO chargedto)throws ServiceBizException;///增加
    
    public void updateCharge(String id,ChargeDTO chargeDTO)throws ServiceBizException;///修改
    
    public void deleteChargeById(String id)throws ServiceBizException;///删除
    
	public List<Map> selectCharge()throws ServiceBizException;
    
    public Map queryChargeByCode(String code)throws ServiceBizException;
    
    public Map queryChargeByName(String name)throws ServiceBizException;
    public List<Map> selectCharge2() throws ServiceBizException;
    

}
