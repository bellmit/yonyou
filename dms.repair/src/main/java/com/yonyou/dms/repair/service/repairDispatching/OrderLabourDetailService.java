/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : OrderLabourDetailService.java
*
* @Author : rongzoujie
*
* @Date : 2016年9月20日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月20日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.repairDispatching;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.commonAS.domains.DTO.order.RoRepairProDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.repairDispatching.OrderDispatchDetailTransFormDTO;

/**
* TODO description
* @author rongzoujie
* @date 2016年9月20日
*/

public interface OrderLabourDetailService {
    public PageInfoDto queryRepairProDetail(Long workerOrderId) throws ServiceBizException;
    public void addReapirProDetail(RoRepairProDTO roReProDto,Long id)throws ServiceBizException;
    public List<Map> queryReapirPro(Long id)throws ServiceBizException;
    public void addOrderLabourDetail(OrderDispatchDetailTransFormDTO oDDTFD)throws ServiceBizException;
    public PageInfoDto queryRepairProDispatchDteail(Long roLabourId)throws ServiceBizException;
    public void setFinishDisPatch(String finishDispatchIds,String unfinishDispatchIds)throws ServiceBizException;
    public void detleteDispatchDetail(Long assignId)throws ServiceBizException;
    public void addAllOrderLabourDetail(OrderDispatchDetailTransFormDTO oDDTFD)throws ServiceBizException;
    public void deleteAllDispatchDetail(Long roId)throws ServiceBizException;
    public void deleteSelectDispatchDetail(String labourIdStr) throws ServiceBizException;
    public PageInfoDto queryFinishWorkCheck(Long roId)throws ServiceBizException;
    public void setCustomerHour(Long assignId,Double customerHour)throws ServiceBizException;
    public void setFinishHour(Long assignId,String finishHour)throws ServiceBizException;
    public void setChecker(Long assignId,String checker,Object itemEndTime)throws ServiceBizException;
    public PageInfoDto dispatchQueryRepairOrder(Map<String, String> queryParam,String...titles) throws ServiceBizException;
    public void cancelCompeleteWork(Long roId) throws ServiceBizException;
    public void setExpectedMaterial(Long roId) throws ServiceBizException;
    public void cancelExpectedMaterial(Long roId) throws ServiceBizException;
    public void setExpectedInfo(Long roId) throws ServiceBizException;
    public void cancelExpectedInfo(Long roId) throws ServiceBizException;
    public List<Map> queryLabourDetail(Long roId) throws ServiceBizException;
    public List<Map> queryRepairAssignForExport(Map<String, String> queryParam, String... titles);
	PageInfoDto queryRepairProDetailForFinish(Long orderWorderId) throws ServiceBizException;
	//设置工单状态
	public void setRepairProDetail(Long roLabourId)throws ServiceBizException;
}
