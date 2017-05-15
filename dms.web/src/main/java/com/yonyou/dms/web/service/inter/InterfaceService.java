package com.yonyou.dms.web.service.inter;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface InterfaceService {
	public PageInfoDto getQuerySql(Map<String, String> queryParam);
	public void deleteUserById(Long id) throws ServiceBizException;

}
