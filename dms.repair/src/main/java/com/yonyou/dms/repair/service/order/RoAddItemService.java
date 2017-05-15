
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : RoAddItemService.java
*
* @Author : ZhengHe
*
* @Date : 2016年8月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月22日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.order;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.commonAS.domains.DTO.order.RoAddItemDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 附加项目service
* @author ZhengHe
* @date 2016年8月22日
*/

public interface RoAddItemService {
    public void addRoAddItem(RoAddItemDTO raiDto,Long roId)throws ServiceBizException;
    public void deleteRoAddItem(Long roId)throws ServiceBizException;
    public List<Map> queryAddRoItem(Long roId)throws ServiceBizException;
}
