package com.yonyou.dcs.de;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @Description:维修工时下发接口（DCS -> DMS）
 * @author xuqinqin 
 */
public interface SEDCS016 {
	
	public String doSend() throws ServiceBizException;

	
}
