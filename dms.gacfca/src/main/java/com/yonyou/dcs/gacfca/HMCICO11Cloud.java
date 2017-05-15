package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @ClassName: HMCICO11Cloud 
 * @Description: TODO(URL功能列表下发) 
 * @author xuqinqin
 * @date 2017-05-04 
 */
public interface HMCICO11Cloud {
	
	public List<String> sendData(List<String> dealerCodes) throws ServiceBizException;
	
}
