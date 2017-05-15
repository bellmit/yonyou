package com.yonyou.dcs.gacfca;

import com.yonyou.dms.common.domains.DTO.common.SalesorderShoppingDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @ClassName: SA01Cloud 
 * @Description: TODO(车辆调拨申请下发) 
 * @author xuqinqin
 */
public interface SA01Cloud {
	
	public String sendData(String dealerCode, SalesorderShoppingDTO vo) throws ServiceBizException;
	
}
