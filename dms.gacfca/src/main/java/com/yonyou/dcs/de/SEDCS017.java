package com.yonyou.dcs.de;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @Description:维修工时参数下发接口（DCS -> DMS）
 * 总部下发维修工时参数代码层级结构
 * @author xuqinqin 
 */
public interface SEDCS017 {
	
	// 全量下发
	public String sendAllInfo(int groupType) throws ServiceBizException;
	
}
