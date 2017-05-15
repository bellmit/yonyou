package com.yonyou.dcs.de;

import java.util.List;

import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @ClassName: HMCICO11Cloud 
 * @Description: TODO(URL功能列表下发) 
 * @author xuqinqin
 */
public interface HMCICO11 {
	
	public List<String> sendData(List<String> dealerCodes) throws ServiceBizException;
	
}
