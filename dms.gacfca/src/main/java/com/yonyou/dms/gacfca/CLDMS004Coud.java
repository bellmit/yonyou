/**
 * 
 */
package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.TmMarketActivityDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @author Administrator
 *
 */
public interface CLDMS004Coud {
	public int getCLDMS004(LinkedList<TmMarketActivityDto> voList,String dealerCode) throws ServiceBizException;
	
}
