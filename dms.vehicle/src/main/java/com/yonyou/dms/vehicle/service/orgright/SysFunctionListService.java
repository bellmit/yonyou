package com.yonyou.dms.vehicle.service.orgright;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SysFunctionListService {

	/**
	 * 查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto queryList(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

}
