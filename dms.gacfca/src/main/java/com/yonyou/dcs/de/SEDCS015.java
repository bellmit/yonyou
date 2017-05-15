package com.yonyou.dcs.de;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @Description:维修工时下发接口（DCS -> DMS）
 * @author xuqinqin 
 */
public interface SEDCS015 {
	
	// 全量下发
	public String sendAllInfo() throws ServiceBizException;

	// 多选下发
	public String sendMoreInfo(String array) throws ServiceBizException;
	
}
