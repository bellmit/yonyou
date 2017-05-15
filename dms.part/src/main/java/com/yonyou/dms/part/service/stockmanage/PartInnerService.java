
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartInnerService.java
*
* @Author : jcsi
*
* @Date : 2016年8月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月15日    jcsi    1.0
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
import com.yonyou.dms.part.domains.DTO.stockmanage.PartInnerDto;
import com.yonyou.dms.part.domains.PO.stockmanage.PartInnerPo;

/**
* 内部领用 接口
* @author jcsi
* @date 2016年8月15日
*/

public interface PartInnerService {
    
    public PageInfoDto search(Map<String, String> param)throws ServiceBizException;
    
    public void deleteById(Long id)throws ServiceBizException;
    
    public PartInnerPo addPartInner(PartInnerDto partInnerDto,String receiptNo)throws ServiceBizException;
    
    public void editPartInner(Long id,PartInnerDto partInnerDto)throws ServiceBizException;
    
    public Map<String,Object> findById(Long id)throws ServiceBizException;
    
    public PageInfoDto findItemById(Long id)throws ServiceBizException;
    
    public void updateOrderStatus(Long id)throws ServiceBizException;
}
