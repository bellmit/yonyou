package com.yonyou.dcs.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @Title: SADCS074Cloud
 * @Description:开票日期下发接口（DCS -> DMS）
 * 总部修改车辆开票日期后，下发所有存有车辆信息的经销商
 * @author xuqinqin 
 */
public interface SADCS074Cloud  extends BaseCloud{

	public String sendData(String vin,String dealerCode) throws ServiceBizException;
	
}
