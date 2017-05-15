package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

/**
 * 索赔类型维护
 * @author Administrator
 *
 */
public interface ClaimTypeService {
	//索赔维护类型查询
	public PageInfoDto  ClaimTypeQuery(Map<String, String> queryParam);

}
