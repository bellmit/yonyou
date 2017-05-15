
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : StuffPriceAdjustService.java
*
* @Author : zhanshiwei
*
* @Date : 2017年5月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月9日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.partOrder;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.AccountPeriodPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartPeriodReportPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPartPeriodReportDTO;
import com.yonyou.dms.part.domains.DTO.partOrder.TtRepairOrderDTO;

/**
* 发料价格调整
* @author zhanshiwei
* @date 2017年5月9日
*/

public interface StuffPriceAdjustService {
public PageInfoDto queryRepairOrder(Map<String,String> queryParam) throws ServiceBizException;
public List<Map> queryPartSendPrice(Map<String,String> queryParam) throws ServiceBizException;
public int CheckLockerValid(Map<String,String> queryParam) throws ServiceBizException;
public void  changePartSendPrice(TtRepairOrderDTO ttRepairDto) throws ServiceBizException, Exception;
public AccountPeriodPO getAccountCyclePO() throws ServiceBizException, Exception;
public int createOrUpdate( TtPartPeriodReportPO db,TtPartPeriodReportDTO db2, AccountPeriodPO account, String entityCode) throws Exception;
}
