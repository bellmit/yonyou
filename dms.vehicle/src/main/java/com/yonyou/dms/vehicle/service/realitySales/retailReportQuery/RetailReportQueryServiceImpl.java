package com.yonyou.dms.vehicle.service.realitySales.retailReportQuery;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.realitySales.retailReportQuery.RetailReportQueryDao;

@Service
public class RetailReportQueryServiceImpl implements RetailReportQueryService {
	
	@Autowired
	RetailReportQueryDao dao;

	@Override
	public PageInfoDto retailReportQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryList(queryParam,loginInfo);
		return pgInfo;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> retailReportQuerySuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		List<Map> list = dao.queryRetailReportQueryForExport(queryParam,loginInfo);
		return list;
	}

	@Override
	public Map<String, Object> queryDetail(Long nvdrId, LoginInfoDto loginInfo) throws ServiceBizException {
		Map<String, Object> map = dao.queryDetail(nvdrId,loginInfo);
		return map;
	}

	@Override
	public PageInfoDto retailDealerReportQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryDealerList(queryParam,loginInfo);
		return pgInfo;
	}

	@Override
	public Map<String, Object> queryDealerDetail(String id, LoginInfoDto loginInfo) throws ServiceBizException {
		Map<String, Object> map = dao.queryDealerDetail(id,loginInfo);
		return map;
	}

	@Override
	public List<Map> retailDealerReportQuerySuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		List<Map> list = dao.queryDealerRetailReportQueryForExport(queryParam,loginInfo);
		return list;
	}

}
