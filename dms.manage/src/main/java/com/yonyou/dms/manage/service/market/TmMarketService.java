package com.yonyou.dms.manage.service.market;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.market.TmMarketActivityDTO;


public interface TmMarketService {
	public PageInfoDto getQuerySql(Map<String, String> queryParam);

	public Long addTcBank(TmMarketActivityDTO tcdto) throws ServiceBizException;

	public void modifyTcBank(Long id, TmMarketActivityDTO tcdto) throws ServiceBizException;
}
