/**
 * 
 */
package com.infoeai.eai.action.bsuv.dccmApp;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.infoeai.eai.wsServer.DccmAppService.SalesConsultantVo;

/**
 * @author Administrator
 *
 */
public interface DCSTODCCM003 {
	public SalesConsultantVo[] getSalesConsultantList(String from,String to) throws Exception;
	
	public SalesConsultantVo[] GetRelevantInformation(String from,String to) throws UnsupportedEncodingException;
}
