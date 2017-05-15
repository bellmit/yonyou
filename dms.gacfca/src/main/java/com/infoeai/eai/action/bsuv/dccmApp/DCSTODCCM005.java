/**
 * 
 */
package com.infoeai.eai.action.bsuv.dccmApp;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.infoeai.eai.wsServer.DccmAppService.CustomerReceptionVo;

/**
 * @author Administrator
 *
 */
public interface DCSTODCCM005 {
	public CustomerReceptionVo[] getCustomerReceptionList(String from,String to) throws Exception;
	public CustomerReceptionVo[] getList(String from,String to) throws UnsupportedEncodingException;
}
