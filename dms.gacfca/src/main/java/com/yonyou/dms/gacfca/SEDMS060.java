/**
 * 
 */
package com.yonyou.dms.gacfca;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.DTO.gacfca.DMSTODCS003Dto;

/**
 * @author Administrator
 *
 */
public interface SEDMS060 {
	public int getSEDMS060(String soNo,String isNotAudit,String flag) throws ParseException, Exception;
	
	@SuppressWarnings("rawtypes")
	public List<Map> querySalesOrder(String soNo) throws Exception;
}
