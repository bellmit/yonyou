package com.yonyou.dms.vehicle.service.claimManage;

import com.yonyou.dms.function.exception.ServiceBizException;

public interface DBLockService {
	
	
	public  boolean lock(String lockId,String businessType) throws ServiceBizException;
	
	public  void freeLock(String lockId,String businessType) throws ServiceBizException;
}
