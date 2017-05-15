package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

/**
 * 索赔工作月查询
 * @author Administrator
 *
 */
public interface  ClaimWorkMonthQueryService {
	public PageInfoDto  ClaimTypeQuery(Map<String, String> queryParam);

}
