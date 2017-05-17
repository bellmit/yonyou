package com.yonyou.dcs.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @Title: SEDCS019Cloud
 * @Description:召回活动下发接口（DCS -> DMS）
 * @author xuqinqin 
 */
public interface SEDCS019Cloud  extends BaseCloud{
	
	public String doSend(String recallId) throws ServiceBizException;
	
}
