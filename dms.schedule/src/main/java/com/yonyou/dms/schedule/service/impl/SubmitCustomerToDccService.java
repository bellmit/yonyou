/**
 * 
 */
package com.yonyou.dms.schedule.service.impl;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SameToDccDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @author Administrator
 *
 */
public interface SubmitCustomerToDccService {
	
	@SuppressWarnings("rawtypes")
	public List queryNeedToDccCustomer() throws ServiceBizException;
	
	void deleteFcustomerAction(String customerNo) throws ServiceBizException;
	
	void updateCustomerToF() throws  Exception;
	
	public List<SameToDccDto> performExecute() throws ServiceBizException;
}
