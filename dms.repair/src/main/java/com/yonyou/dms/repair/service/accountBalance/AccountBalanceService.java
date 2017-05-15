package com.yonyou.dms.repair.service.accountBalance;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;

@SuppressWarnings("all")
public interface AccountBalanceService {
	/**
	 * 账户余额查询
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto accountBalanceQuery(Map<String, String> queryParam);

	/**
	 * 账户余额查询下载
	 * 
	 * @param queryParam
	 *
	 */
	void doDownload(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request);

	/**
	 * 账户异动明细查询
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto accountChangeDetailQuery(Map<String, String> queryParam);

	/**
	 * 账户异动明细查询下载
	 * 
	 * @param queryParam
	 * @param response
	 * @param request
	 */
	void QueryDownload(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request);

	/**
	 * 返利发放查询
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto rebateIssueQuery(Map<String, String> queryParam);

	/**
	 * 返利发放查询下载
	 * 
	 * @param queryParam
	 * @param response
	 * @param request
	 */
	void rebateIssueDownLoad(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request);

	/**
	 * 返利类型下拉框回显
	 * 
	 * @param queryParam
	 * @return
	 */
	List<Map> rebateTypeCode(Map<String, String> queryParam);

	/**
	 * 经销商资金应付查询
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto dealerPayQuery(Map<String, String> queryParam);

	/**
	 * 经销商资金应付查询
	 * 
	 * @param queryParam
	 * @return
	 */
	List<Map> dealerPayquery1(Map<String, String> queryParam);

}
