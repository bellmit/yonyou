package com.yonyou.dms.repair.service.k4RebateManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface RebateBalanceService {
	/**
	 * 经销商返利余额查询
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto rebateBalanceQuery(Map<String, String> queryParam);

	/**
	 * 经销商返利余额查询下载
	 * 
	 * @param queryParam
	 * @return
	 */
	void doDownload(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request);

	/**
	 * 经销商返利余额查询
	 * 
	 * @param queryParam
	 * @return
	 */
	List<Map> rebateBalanceDealerQuery(Map<String, String> queryParam);

	/**
	 * 返利使用明细查询
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto rebateUseDetailQuery(Map<String, String> queryParam);

	void rebateUseDetailDownLoad(Map<String, String> queryParam, HttpServletResponse response,
			HttpServletRequest request);

}
