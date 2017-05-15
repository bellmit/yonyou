package com.yonyou.dms.repair.controller.k4BasicPricing;

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
import com.yonyou.dms.repair.service.basicPricing.BasicPricingService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 基础定价查询
 * 
 * @author lingxinglu
 *
 */
@Controller
@TxnConn
@RequestMapping("/basicPricing")
@ResponseBody
public class BasicPricingController {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(BasicPricingController.class);
	@Autowired
	private BasicPricingService service;

	@RequestMapping(value = "/basicPricingQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto basicPricingQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============基础定价查询===============");
		PageInfoDto pageInfoDto = service.basicPricingQuery(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============基础定价查询下载===============");
		service.doDownload(queryParam, response, request);

	}

}
