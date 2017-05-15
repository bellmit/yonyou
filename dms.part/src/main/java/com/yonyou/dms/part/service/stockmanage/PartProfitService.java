
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartProfitService.java
*
* @Author : xukl
*
* @Date : 2016年8月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月12日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.stockmanage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TtPartProfitPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartProfitDto;

/**
* PartProfitService
* @author xukl
* @date 2016年8月12日
*/
@SuppressWarnings("rawtypes")
public interface PartProfitService {

    public PageInfoDto qryPartProfit(Map<String, String> queryParam) throws ServiceBizException;

    public TtPartProfitPO getPartProfitById(Long id) throws ServiceBizException;

    public List<Map> getPartProfitItemsById(Long masterid) throws ServiceBizException;

    public TtPartProfitPO addPartProfit(String profitNo, PartProfitDto partProfitDto) throws ServiceBizException;

    public void updatePartProfit(Long id, PartProfitDto partProfitDto) throws ServiceBizException;

    public void deletePartProfitbyId(Long id) throws ServiceBizException;

    public void doProfitInWhouse(Long id) throws ServiceBizException;

}
