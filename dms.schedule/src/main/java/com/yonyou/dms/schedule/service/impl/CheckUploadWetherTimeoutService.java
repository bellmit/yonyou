/**
 * 
 */
package com.yonyou.dms.schedule.service.impl;

import java.text.ParseException;
import java.util.List;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 每天将400逾期未进行二次补录的数据标为逾期
 * @author xhy
 * @date 2017年2月23号
 */
public interface CheckUploadWetherTimeoutService {
	public int performExecute() throws ServiceBizException;
	
	@SuppressWarnings("rawtypes")
	public List QueryOverTime() throws ServiceBizException , ParseException;
	
}
