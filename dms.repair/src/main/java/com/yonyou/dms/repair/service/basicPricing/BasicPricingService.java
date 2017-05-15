package com.yonyou.dms.repair.service.basicPricing;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface BasicPricingService {
	/**
	 * 基础定价查询
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto basicPricingQuery(Map<String, String> queryParam);

	/**
	 * 基础定价查询下载
	 * 
	 * @param queryParam
	 * @param response
	 * @param request
	 */
	void doDownload(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request);

}
