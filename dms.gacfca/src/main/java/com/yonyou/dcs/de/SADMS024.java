package com.yonyou.dcs.de;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @Description:限价车辆下发接口（DCS -> DMS）
 * @author xuqinqin 
 */
public interface SADMS024 {
	
	public String sendData(String limitId,String  dsFlag) throws ServiceBizException;
	
}
