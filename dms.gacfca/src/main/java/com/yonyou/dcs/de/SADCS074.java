package com.yonyou.dcs.de;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @Description:开票日期下发接口
 * @author xuqinqin 
 */
public interface SADCS074 {
	
	public String sendData(String vin,String dealerCode) throws ServiceBizException;
	
}
