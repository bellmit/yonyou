package com.yonyou.dcs.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 一对一客户经理绑定修改下发
 * 
 * @author Administrator
 *
 */
public interface SADCS021Cloud {
	public String handleExecute() throws ServiceBizException;

	// public static LinkedList<String> sendData() throws Exception;
	public String sendData(String param) throws Exception;
}
