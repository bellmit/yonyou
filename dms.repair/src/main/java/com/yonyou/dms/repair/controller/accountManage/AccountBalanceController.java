package com.yonyou.dms.repair.controller.accountManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.repair.service.accountBalance.AccountBalanceService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * JV账户管理
 * 
 * @author lingxinglu
 *
 */
@Controller
@TxnConn
@RequestMapping("/k4AccountManage")
@ResponseBody
@SuppressWarnings("all")
public class AccountBalanceController {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(AccountBalanceController.class);
	@Autowired
	private AccountBalanceService service;

	@RequestMapping(value = "/accountBalanceQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto accountBalanceQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============账户余额查询===============");
		PageInfoDto pageInfoDto = service.accountBalanceQuery(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============账户余额查询下载===============");
		service.doDownload(queryParam, response, request);

	}

	@RequestMapping(value = "/accountChangeDetailQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto accountChangeDetailQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============账户异动明细查询===============");
		PageInfoDto pageInfoDto = service.accountChangeDetailQuery(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/downLoad", method = RequestMethod.GET)
	public void queryDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============账户异动明细查询下载===============");
		service.QueryDownload(queryParam, response, request);

	}

	@RequestMapping(value = "/rebateIssueQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto rebateIssueQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============返利发放查询===============");
		PageInfoDto pageInfoDto = service.rebateIssueQuery(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/rebateTypeCode", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> rebateTypeCode(@RequestParam Map<String, String> queryParam) {
		logger.info("============返利类型下拉框回显===============");
		List<Map> map = service.rebateTypeCode(queryParam);
		return map;
	}

	@RequestMapping(value = "/rebateIssueDownLoad", method = RequestMethod.GET)
	public void rebateIssueDownLoad(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============返利发放查询下载===============");
		service.rebateIssueDownLoad(queryParam, response, request);

	}

	@RequestMapping(value = "/dealerPayQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto dealerPayQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============经销商资金应付查询===============");
		PageInfoDto pageInfoDto = service.dealerPayQuery(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/dealerPayQuery1", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> dealerPayQuery1(@RequestParam Map<String, String> queryParam) {
		logger.info("============经销商资金应付查询===============");
		List<Map> map = service.dealerPayquery1(queryParam);
		return map;
	}
}
