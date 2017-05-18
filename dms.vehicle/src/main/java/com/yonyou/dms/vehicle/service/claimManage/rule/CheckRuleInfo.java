package com.yonyou.dms.vehicle.service.claimManage.rule;

import java.util.Map;


public interface CheckRuleInfo {	
	public Map<String,Object> executeCheckStep(Map<String,Object> params,Map<String,Object> message);

}
