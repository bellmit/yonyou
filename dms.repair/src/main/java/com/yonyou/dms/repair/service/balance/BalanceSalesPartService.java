
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BalanceSalesPartService.java
*
* @Author : ZhengHe
*
* @Date : 2016年11月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年11月10日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.balance;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.balance.BalanceSalesPartDTO;

/**
* 结算销售配件明细
* @author ZhengHe
* @date 2016年11月10日
*/

public interface BalanceSalesPartService {
    
    public Long addBalanceSalesPart(BalanceSalesPartDTO bSPDto)throws ServiceBizException;
}
