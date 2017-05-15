/**
 * 
 */
package com.infoeai.eai.action.bsuv.dccmApp;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.infoeai.eai.wsServer.DccmAppService.CustomerStatusVo;

/**
 * @author Administrator
 *
 */
public interface DCSTODCCM006 {
	public CustomerStatusVo[] getCustomerStatusList(String from,String to) throws Exception;
	
	public CustomerStatusVo[] getList(String from,String to) throws UnsupportedEncodingException;
}
