/**
 * 
 */
package com.yonyou.dms.customer.service.checkCarAlarm;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @author sqh
 *
 */
public interface CheckCarAlarmService {

	/**
	 * 查询验车到期提醒
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto queryCheckCarAlarm(Map<String, String> queryParam) throws ServiceBizException;
	
}
