package com.yonyou.dms.customer.service.OwnerVehicle;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface WeChatService {
	/**
	 * 查询微信回传
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto queryWeChatInfo(Map<String, String> queryParam) throws ServiceBizException;
}
