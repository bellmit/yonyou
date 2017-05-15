package com.yonyou.dcs.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @Title: SEDCS015Cloud
 * @Description:维修工时下发 接口（DCS -> DMS）
 * @author xuqinqin 
 */
public interface SEDCS015Cloud {
	//全量下发
	public String sendAllInfo() throws ServiceBizException;
	//多选下发
	public String sendMoreInfo(String array) throws ServiceBizException;
}
