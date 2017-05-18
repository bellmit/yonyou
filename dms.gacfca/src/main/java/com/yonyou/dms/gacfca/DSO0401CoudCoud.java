package com.yonyou.dms.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：经销商上报批售审批单
 * 
 * @author Benzc
 * @date 2017年1月12日
 *
 */
public interface DSO0401CoudCoud {
	
	public int getDSO0401(String wsNo) throws ServiceBizException;

}
