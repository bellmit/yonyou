package com.yonyou.dms.manage.service.basedata.org;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;

public interface OrgManageService {

	List<Map> getBigOrg(Map<String, String> queryParams) throws ServiceBizException;
	
	List<Map> getSmallOrg(String bigorgid, Map<String, String> queryParams) throws ServiceBizException;

}
