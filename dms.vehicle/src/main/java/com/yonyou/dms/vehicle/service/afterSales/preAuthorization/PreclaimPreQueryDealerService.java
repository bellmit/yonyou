package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

/**
 * 经销商端索赔预授权状态查询
 * @author Administrator
 *
 */
public interface PreclaimPreQueryDealerService {
	//经销商端索赔预授权状态查询
	public PageInfoDto PreclaimPreQueryDealerQuery(Map<String, String> queryParam);

}
