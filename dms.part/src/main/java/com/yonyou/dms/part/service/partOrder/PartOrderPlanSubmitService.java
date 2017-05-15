
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartOrderPlanSubmitService.java
*
* @Author : zhanshiwei
*
* @Date : 2017年5月3日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月3日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.service.partOrder;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPtDmsOrderDTO;

/**
 * 配件上报
 * 
 * @author zhanshiwei
 * @date 2017年5月3日
 */

public interface PartOrderPlanSubmitService {

    public PageInfoDto queryDmsPtOrderInfo(Map<String, String> queryParam) throws ServiceBizException;

    public Map<String, Object> modifyDmsPtOrderInfo(String OrderNo) throws ServiceBizException;

    public void modifyUpdateDmsPtOrderRemark(String orderNo, TtPtDmsOrderDTO ttPtOrderDto) throws ServiceBizException;

    public PageInfoDto queryDmsPtOrderItemInfo(Map<String, String> queryParam) throws ServiceBizException;

    public List<Map> queryDmsPtOrderItemInfoExport(Map<String, String> queryParam) throws Exception;

    public void MaintainDmsPtOrderAchieve(TtPtDmsOrderDTO ttPtOrderDto) throws ServiceBizException;

    public void deleteDmsPtOrderPlan(TtPtDmsOrderDTO ttPtOrderDto) throws ServiceBizException;
    public List<Map> queryMoDmsPtOrderItemInfoExport(Map<String, String> queryParam) throws Exception;
}
