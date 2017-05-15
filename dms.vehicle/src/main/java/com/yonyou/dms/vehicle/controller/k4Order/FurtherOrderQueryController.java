package com.yonyou.dms.vehicle.controller.k4Order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.k4Order.FurtherOrderQueryDao;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.K4OrderDTO;
import com.yonyou.dms.vehicle.service.k4Order.FurtherOrderQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * @author liujiming
 * @date 2017年3月7日
 */
@Controller
@TxnConn
@RequestMapping("/furtherOrderQuery")
public class FurtherOrderQueryController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(FurtherOrderQueryController.class);

	@Autowired
	private FurtherOrderQueryDao furtherQuDao;

	// @Autowired
	// private ExcelGenerator excelService;

	@Autowired
	private FurtherOrderQueryService furtherQuService;

	/**
	 * 期货订单撤单查询
	 * 
	 * @param queryParam
	 *            查询条件
	 */
	@RequestMapping(value = "/furtherOrder/query", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto furtherOrderQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============期货订单撤单查询10===============");
		PageInfoDto pageInfoDto = furtherQuDao.getFurtherOrderQueryList(queryParam);

		return pageInfoDto;

	}

	/**
	 * 期货订单撤单下载
	 * 
	 * @param queryParam
	 *            查询条件
	 */
	@RequestMapping(value = "/furtherOrder/download", method = RequestMethod.GET)
	public void furtherOrderDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============期货订单撤单下载10===============");
		furtherQuService.furtherOrderDownload(queryParam, request, response);

	}

	/**
	 * 期货订单撤单
	 */
	@RequestMapping(value = "/furtherOrder/cancle", method = RequestMethod.PUT)
	@ResponseBody
	public void furtherOrderCancle(@RequestBody @Valid K4OrderDTO k4OrderDTO) {
		// System.out.println("queryInitController");
		logger.info("============期货订单撤单10===============");
		furtherQuService.modifyFurtherOrderCancle(k4OrderDTO);

	}

}
