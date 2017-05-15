package com.yonyou.dcs.de;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @Description:保险营销活动下发接口
 * @author xuqinqin 
 */
public interface SADCS075 {
	
	public String sendData(String actId) throws ServiceBizException;
	
}
