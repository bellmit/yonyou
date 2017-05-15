
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartLossService.java
*
* @Author : jcsi
*
* @Date : 2016年8月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月13日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.stockmanage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartLossDto;
import com.yonyou.dms.part.domains.PO.stockmanage.PartLossPo;

/**
* 配件报损 （接口）
* @author jcsi
* @date 2016年8月13日
*/

public interface PartLossService {

    
    public PageInfoDto searchPartLoss(Map<String, String> param)throws ServiceBizException;

    public void deleteById(Long id)throws ServiceBizException;
    
    public PartLossPo addPartLoss(PartLossDto partLossDto,String LossNo )throws ServiceBizException;
    
    public void editPartLoss(Long id,PartLossDto partLossDto)throws ServiceBizException;
    
    public Map findLossById(Long id)throws ServiceBizException;
    
    public PageInfoDto findLossItemById(Long id)throws ServiceBizException;
    
    public void updateOrderStatus(Long id)throws ServiceBizException;
}
