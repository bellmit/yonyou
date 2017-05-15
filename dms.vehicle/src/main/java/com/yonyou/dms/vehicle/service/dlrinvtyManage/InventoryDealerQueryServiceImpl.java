package com.yonyou.dms.vehicle.service.dlrinvtyManage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.dlrinvtyManage.InventoryDealerQueryDao;

@Service
public class InventoryDealerQueryServiceImpl implements InventoryDealerQueryService {
	
	@Autowired
	InventoryDealerQueryDao dao;

	/**
	 * 汇总查询
	 */
	@Override
	public PageInfoDto oemInventoryGroupQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		PageInfoDto resultList = dao.queryInventoryGroupList(queryParam,loginInfo);
		return resultList;
	}

	/**
	 * 明细查询
	 */
	@Override
	public PageInfoDto oemInventoryDetailQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryInventoryDetailList(queryParam,loginInfo);
		return pgInfo;
	}

	/**
	 * 明细下载
	 */
	@Override
	public List<Map> findTotalQuerySuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		List<Map> list = dao.queryTotalForExport(queryParam,loginInfo);
		return list;
	}

}
