package com.yonyou.dms.customer.service.OwnerVehicle;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface WeChatUpdateService {
	/**
	 * 查询客户经理回传
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto queryWeChatInfo(Map<String, String> queryParam) throws ServiceBizException;
}
