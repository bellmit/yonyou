
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartAllocateOutService.java
*
* @Author : jcsi
*
* @Date : 2016年7月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月26日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.stockmanage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.commonAS.domains.PO.basedata.PartAllocateOutPo;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartAllocateOutDto;

/**
* 调拨出库 接口
* @author jcsi
* @date 2016年7月26日
*/

public interface PartAllocateOutService {
    
    public PageInfoDto search(Map<String, String> param)throws ServiceBizException;
    
    public PageInfoDto searchBalance(Map<String,String> param)throws ServiceBizException;
    
    public void delete(Long id)throws ServiceBizException;
    
    public PartAllocateOutPo addPartOutAndOutItem(String allocateOutNo,PartAllocateOutDto partAllocateOutDto)throws ServiceBizException;
    
    public Map<String,Object> findById(Long id)throws ServiceBizException;
    
    public void editPartAllocateOutDto(Long id,PartAllocateOutDto partAllocateOutDto)throws ServiceBizException;

    public List<Map> searchOutItemByOutId(Long id)throws ServiceBizException;
    
    public void updateOrderStatusById(Long id)throws ServiceBizException;
    
    public List<Map> searchBalanceByNo(Map<String,String> param)throws ServiceBizException;
}
