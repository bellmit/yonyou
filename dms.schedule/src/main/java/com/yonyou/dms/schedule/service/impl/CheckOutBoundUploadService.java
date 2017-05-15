/**
 * 
 */
package com.yonyou.dms.schedule.service.impl;

import java.util.List;


import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 监控交车确认时保修登记信息上报情况(未成功上报的重新上报) 
 * @author Administrator
 * @date 2017年2月23日
 *
 */
public interface CheckOutBoundUploadService {
	public int performExecute() throws ServiceBizException;
	
	@SuppressWarnings("rawtypes")
	public List  queryMatchData() throws ServiceBizException;
	
	
	
}
