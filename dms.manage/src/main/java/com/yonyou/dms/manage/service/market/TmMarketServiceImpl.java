package com.yonyou.dms.manage.service.market;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.market.MarketActivityCreateDao;
import com.yonyou.dms.manage.domains.DTO.market.TmMarketActivityDTO;
@Service
public class TmMarketServiceImpl implements TmMarketService{
	@Autowired
	MarketActivityCreateDao dao;

	@Override
	public PageInfoDto getQuerySql(Map<String, String> queryParam) {
	
		return dao.marketActivityQueryList(queryParam);
	}

	@Override
	public Long addTcBank(TmMarketActivityDTO tcdto) throws ServiceBizException {

		return dao.addMarket(tcdto);
	}

	@Override
	public void modifyTcBank(Long id, TmMarketActivityDTO tcdto) throws ServiceBizException {
		dao.modifyMarket(id, tcdto);
		
	}

}
