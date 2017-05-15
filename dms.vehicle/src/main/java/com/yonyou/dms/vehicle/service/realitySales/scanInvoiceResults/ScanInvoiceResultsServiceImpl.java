package com.yonyou.dms.vehicle.service.realitySales.scanInvoiceResults;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.realitySales.scanInvoiceResults.ScanInvoiceResultsDao;

@Service
public class ScanInvoiceResultsServiceImpl implements ScanInvoiceResultsService {
	
	@Autowired
	ScanInvoiceResultsDao dao;

	/**
	 * 发票扫描结果查询
	 */
	@Override
	public PageInfoDto scanInvoiceResultsQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryList(queryParam,loginInfo);
		return pgInfo;
	}

	/**
	 * 下载
	 */
	@Override
	public List<Map> scanInvoiceResultsDownLoadList(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		List<Map> list = dao.scanInvoiceResultstQueryForExport(queryParam,loginInfo);
		return list;
	}

	/**
	 * 详细
	 */
	@Override
	public Map<String, Object> queryDealerDetail(Long id, LoginInfoDto loginInfo) throws ServiceBizException {
		Map<String, Object> map = dao.queryDealerDetail(id,loginInfo);
		return map;
	}

}
