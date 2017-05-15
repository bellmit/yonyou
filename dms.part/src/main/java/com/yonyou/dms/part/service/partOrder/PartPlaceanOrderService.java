
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartOrderService.java
*
* @Author : zhanshiwei
*
* @Date : 2017年4月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月14日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.service.partOrder;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPtDmsOrderDTO;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPtDmsOrderItemDTO;
import com.yonyou.dms.part.domains.PO.partOrder.TtPtDmsOrderPO;

/**
 * 配件订货
 * 
 * @author zhanshiwei
 * @date 2017年4月14日
 */

public interface PartPlaceanOrderService {

    public PageInfoDto queryAllocateInOrders(Map<String, String> queryParam) throws ServiceBizException, Exception;

    public PageInfoDto QueryDmsSanBaoOrderPartInfo(Map<String, String> queryParam) throws ServiceBizException,
                                                                                   Exception;

    public List<TtPtDmsOrderPO> CreateNewDMSOrder(TtPtDmsOrderItemDTO ttPtOrderIntenDto,
                                                  String orderNo) throws ServiceBizException;

    public List<Map> queryDetail(Map<String, String> queryParam, String orderNo) throws ServiceBizException;

    public PageInfoDto queryDmsPartInOutSub(Map<String, String> queryParam, String partNo,
                                            String storageCode) throws ServiceBizException;

    public List<Map> queryDmsPartOption(String partNo) throws ServiceBizException;

    public Map maintainDmsPtOrder(TtPtDmsOrderDTO ttptdmsorderdto) throws ServiceBizException, Exception;

    public List<Map> queryDmsPartOrderFormula(Map<String, String> queryParam) throws ServiceBizException;

    public List<Map> queryDmsSuggestOrder(@RequestParam Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto queryDmsPartInfoAboutOrder(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto queryPartSalesHistory(Map<String, String> queryParam, String partNo, String storageCode);

    public PageInfoDto queryDmsPartForOrder(Map<String, String> queryParam);

    public List<Object> queryDmsDeliverPartsReplace(Map<String, String> queryParam) throws ServiceBizException;
    
    public int deleteDmsPtOrderPlan(TtPtDmsOrderDTO ttptdmsorderdto)throws ServiceBizException,
    Exception;

}
