/**
 * 
 */
package com.yonyou.dms.gacfca;


import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.CLDMS002Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @author Administrator
 *
 */
public interface CLDMS002Coud {
	public int getCLDMS002(LinkedList<CLDMS002Dto> voList,String dealerCode) throws ServiceBizException;
}
