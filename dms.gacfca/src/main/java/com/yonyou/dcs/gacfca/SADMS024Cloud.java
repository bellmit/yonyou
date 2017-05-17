package com.yonyou.dcs.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @Title: SADMS024Cloud
 * @Description:限价车辆下发接口（DCS -> DMS）
 * @author xuqinqin 
 */
public interface SADMS024Cloud  extends BaseCloud{

	public String sendData(String limitId,String  dsFlag) throws ServiceBizException;
	
}
