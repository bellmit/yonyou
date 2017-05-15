package com.yonyou.dcs.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;

public interface SADMS064Cloud {
	
	public void sendData(Long bankId,String dealerCode) throws Exception;

	public String handleExecute() throws ServiceBizException;

}
