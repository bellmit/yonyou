package com.yonyou.dms.manage.service.basedata.org;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;

public interface OemDeptService {

	public List<Map> getOemDeptTree() throws ServiceBizException;

	public Map getOrgByCode(String orgCode) throws ServiceBizException;

}
