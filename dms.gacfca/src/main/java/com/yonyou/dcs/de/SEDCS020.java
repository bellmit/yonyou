package com.yonyou.dcs.de;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @Description:召回活动取消下发接口
 * @author xuqinqin 
 */
public interface SEDCS020 {
	
	public String doSend(String recallId) throws ServiceBizException;
	
}
