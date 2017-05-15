package com.yonyou.dms.vehicle.service.realitySales.scanInvoiceQuery;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.realitySales.scanInvoiceQuery.ScanInvoiceQueryDao;

@Service
public class ScanInvoiceQueryServiceImpl implements ScanInvoiceQueryService {
	
	@Autowired
	ScanInvoiceQueryDao dao;

	/**
	 * 汇总查询
	 */
	@Override
	public PageInfoDto oemScanInvoiceTotalQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryInventoryTotalList(queryParam,loginInfo);
		return pgInfo;
	}

	/**
	 * 明细查询
	 */
	@Override
	public PageInfoDto oemScanInvoiceQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryInventoryList(queryParam,loginInfo);
		return pgInfo;
	}

	/**
	 * 汇总下载
	 */
	@Override
	public List<Map> findTotalQuerySuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		List<Map> list = dao.queryTotalForExport(queryParam,loginInfo);
		return list;
	}

	/**
	 * 明细下载
	 */
	@Override
	public List<Map> findInventoryQuerySuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		List<Map> list = dao.queryInventoryForExport(queryParam,loginInfo);
		return list;
	}

}
