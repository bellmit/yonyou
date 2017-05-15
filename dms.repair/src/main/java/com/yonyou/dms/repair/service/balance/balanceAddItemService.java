
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : balanceAddItemService.java
*
* @Author : ZhengHe
*
* @Date : 2016年10月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月11日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.balance;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.balance.BalanceAddItemDTO;

/**
* 结算单附加项目
* @author ZhengHe
* @date 2016年10月11日
*/

public interface balanceAddItemService {

    public Long addBalanceAddItem(BalanceAddItemDTO bAIDto)throws ServiceBizException;
}
