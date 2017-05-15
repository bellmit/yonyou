
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartCostAdjustService.java
*
* @Author : jcsi
*
* @Date : 2016年7月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月15日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.PartCostAdjustDto;

/**
* 配件成本价调整流水账 接口
* @author jcsi
* @date 2016年7月15日
*/

public interface PartCostAdjustService {

    public PageInfoDto search(Map<String, String> param)throws ServiceBizException;
    
    public Map<String,Object> findStoreById(String id)throws ServiceBizException;
    
    public void update(String psId,String psIds,Map pcaDto)throws ServiceBizException;
    
}
