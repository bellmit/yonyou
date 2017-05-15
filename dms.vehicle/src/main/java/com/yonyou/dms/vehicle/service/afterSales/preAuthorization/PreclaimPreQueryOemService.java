package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

/**
 * 索赔预授权状态查询

 * @author Administrator
 *
 */
public interface PreclaimPreQueryOemService {
	//索赔预授权状态查询
	public PageInfoDto PreclaimPreQueryOemQuery(Map<String, String> queryParam);

}
