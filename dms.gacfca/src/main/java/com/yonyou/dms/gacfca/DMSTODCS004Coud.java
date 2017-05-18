/**
 * 
 */
package com.yonyou.dms.gacfca;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.DTO.gacfca.DMSTODCS004Dto;

/**
 * @author Administrator
 *
 */
public interface DMSTODCS004Coud {
	public int getDMSTODCS004(String soNo,String deliveryModeElec) throws ParseException, Exception;
	
	@SuppressWarnings("rawtypes")
	public List<Map> querySalesOrder(String soNo) throws Exception;
}
