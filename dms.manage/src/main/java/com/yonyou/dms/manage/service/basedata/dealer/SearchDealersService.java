package com.yonyou.dms.manage.service.basedata.dealer;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SearchDealersService {

	public List<Map> getDealerList(Map<String, String> queryParams) throws ServiceBizException;

	public List<Map> searchCheckDealer(Map<String, String> queryParams) throws ServiceBizException;

	public PageInfoDto getDealerShouHouList(Map<String, String> queryParams)throws ServiceBizException;

}
