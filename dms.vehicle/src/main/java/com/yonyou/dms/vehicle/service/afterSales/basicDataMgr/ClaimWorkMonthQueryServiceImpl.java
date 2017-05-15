package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr.ClaimWorkMonthQueryDao;

/**
 * 索赔工作月查询
 * @author Administrator
 *
 */
@Service
public class ClaimWorkMonthQueryServiceImpl implements ClaimWorkMonthQueryService{
	@Autowired
	ClaimWorkMonthQueryDao  claimWorkMonthQueryDao;

	@Override
	public PageInfoDto ClaimTypeQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return claimWorkMonthQueryDao.ClaimTypeQuery(queryParam);
	}

}
