package com.yonyou.dcs.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @Title: SEDCS020Cloud
 * @Description:召回活动取消 下发接口（DCS -> DMS）
 * @author xuqinqin 
 */
public interface SEDCS020Cloud  extends BaseCloud{
	
	public String doSend(String recallId) throws ServiceBizException;
	
}
