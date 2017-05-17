package com.yonyou.dcs.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;

public interface SADMS064Cloud {
	
	public int sendData(Long bankId,String dealerCode) throws ServiceBizException;

	public String handleExecute() throws ServiceBizException;

}
