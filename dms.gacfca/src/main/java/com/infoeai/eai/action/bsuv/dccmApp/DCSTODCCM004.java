/**
 * 
 */
package com.infoeai.eai.action.bsuv.dccmApp;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.infoeai.eai.wsServer.DccmAppService.PotentialCustomerVo;

/**
 * @author Administrator
 *
 */
public interface DCSTODCCM004 {
	public PotentialCustomerVo[] getThePotentialCustomerList(String from,String to) throws Exception;
	public PotentialCustomerVo[] GetRelevantInformation(String from,String to) throws UnsupportedEncodingException;
}
