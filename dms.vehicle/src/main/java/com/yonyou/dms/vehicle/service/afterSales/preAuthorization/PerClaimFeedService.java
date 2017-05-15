package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

/**
 * 索赔预授权反馈
 * @author Administrator
 *
 */
public interface PerClaimFeedService {
	
	 // 索陪预授权反馈信息查询
	
	public PageInfoDto PerClaimFeedQuery(Map<String, String> queryParam) ;
}
