/**
 * 
 */
package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.CLDMS009Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @author Administrator
 *
 */
public interface CLDMS009 {
	public int getCLDMS009(LinkedList<CLDMS009Dto> volist,String dealerCode) throws ServiceBizException;
}
