package com.yonyou.dcs.de;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @Description:车主资料下发接口
 * @author xuqinqin 
 */
public interface SADCS072 {
	
	public String sendData(String vin,String dealerCode) throws ServiceBizException;
	
}
