package com.yonyou.dcs.de;

import com.yonyou.dms.common.domains.DTO.common.SalesorderShoppingDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @Description:车辆调拨申请接口
 * @author xuqinqin 
 */
public interface SA01 {
	
	public String sendData(String dealerCode, SalesorderShoppingDTO vo) throws ServiceBizException;
	
}
