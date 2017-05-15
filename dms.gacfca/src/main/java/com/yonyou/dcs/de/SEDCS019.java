package com.yonyou.dcs.de;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @Description:召回活动下发接口
 * @author xuqinqin 
 */
public interface SEDCS019 {
	
	public String doSend(String recallId) throws ServiceBizException;
	
}
