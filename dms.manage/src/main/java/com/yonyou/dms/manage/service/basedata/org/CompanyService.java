package com.yonyou.dms.manage.service.basedata.org;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface CompanyService {

	public PageInfoDto getCompanyList(Map<String, String> queryParams, int i) throws ServiceBizException;

}
