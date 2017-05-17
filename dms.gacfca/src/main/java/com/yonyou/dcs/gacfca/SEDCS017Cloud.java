package com.yonyou.dcs.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @Title: SEDCS017Cloud
 * @Description:维修工时参数下发 接口（DCS -> DMS）
 * @author xuqinqin 
 */
public interface SEDCS017Cloud  extends BaseCloud{
	//全量下发
	public String sendAllInfo(int groupType) throws ServiceBizException;
	
}
