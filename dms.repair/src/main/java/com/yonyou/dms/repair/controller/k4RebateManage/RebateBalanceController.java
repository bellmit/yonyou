package com.yonyou.dms.repair.controller.k4RebateManage;

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
import com.yonyou.dms.repair.controller.accountManage.AccountBalanceController;
import com.yonyou.dms.repair.service.k4RebateManage.RebateBalanceService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * JV经销商返利管理
 * 
 * @author lingxinglu
 *
 */
@Controller
@TxnConn
@RequestMapping("/k4RebateManage")
@ResponseBody
public class RebateBalanceController {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(AccountBalanceController.class);
	@Autowired
	private RebateBalanceService service;

	@RequestMapping(value = "/rebateBalanceQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto rebateBalanceQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============经销商返利余额查询===============");
		PageInfoDto pageInfoDto = service.rebateBalanceQuery(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/rebateBalanceDealerQuery", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> rebateBalanceDealerQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============经销商返利余额查询===============");
		List<Map> m = service.rebateBalanceDealerQuery(queryParam);
		return m;
	}

	@RequestMapping(value = "/rebateDownload", method = RequestMethod.GET)
	public void Download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============经销商返利余额查询下载===============");
		service.doDownload(queryParam, response, request);

	}

	@RequestMapping(value = "/rebateUseDetail", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto rebateUseDetail(@RequestParam Map<String, String> queryParam) {
		logger.info("============返利使用明细查询===============");
		PageInfoDto pageInfoDto = service.rebateUseDetailQuery(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/rebateUseDetailDownLoad", method = RequestMethod.GET)
	public void rebateUseDetailDownLoad(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============返利使用明细查询下载===============");
		service.rebateUseDetailDownLoad(queryParam, response, request);

	}

}
