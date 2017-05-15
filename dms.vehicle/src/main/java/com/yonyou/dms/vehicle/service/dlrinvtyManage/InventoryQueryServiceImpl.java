package com.yonyou.dms.vehicle.service.dlrinvtyManage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.dlrinvtyManage.InventoryQueryDao;

@Service
public class InventoryQueryServiceImpl implements InventoryQueryService {
	
	@Autowired
	InventoryQueryDao dao;

	/**
	 * 经销商库存汇总查询
	 */
	@Override
	public PageInfoDto oemInventoryTotalQuery(Map<String, String> queryParam, LoginInfoDto loginInfo) throws ServiceBizException {
		PageInfoDto resultList = dao.queryInventoryTotalList(queryParam,loginInfo);
		return resultList;
	}

	/**
	 * 经销商库存明细查询(oem)
	 */
	@Override
	public PageInfoDto oemInventoryQuery(Map<String, String> queryParam, LoginInfoDto loginInfo) throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryInventoryList(queryParam,loginInfo);
		return pgInfo;
	}

	/**
	 * 经销商库存汇总查询信息下载
	 */
	@Override
	public List<Map> findTotalQuerySuccList(Map<String, String> queryParam, LoginInfoDto loginInfo) throws ServiceBizException {
		List<Map> list = dao.queryTotalForExport(queryParam,loginInfo);
		return list;
	}

	/**
	 * 经销商库存明细查询信息下载
	 */
	@Override
	public List<Map> findInventoryQuerySuccList(Map<String, String> queryParam, LoginInfoDto loginInfo) throws ServiceBizException {
		List<Map> list = dao.queryInventoryForExport(queryParam,loginInfo);
		return list;
	}

}
