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
import com.yonyou.dms.vehicle.domains.DTO.k4Order.K4OrderDTO;
import com.yonyou.dms.vehicle.service.k4Order.DealerOrderAuditService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 *
 * 
 * @author liujiming
 * @date 2017年2月16日
 */
@Controller
@TxnConn
@RequestMapping("/dealerCancelOrderAreaAudit")
public class DealerCancelOrderAreaAuditController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(DealerCancelOrderAreaAuditController.class);

	@Autowired
	private DealerOrderAuditService dealerOrderAuditService;

	/**
	 * 页面初始化
	 * 
	 * @author liujiming
	 * @date 2017年2月16日
	 * @param queryParam
	 *            查询条件
	 * @return 查询结果
	 */

	// LoginInfoDto loginInfo =
	// ApplicationContextHelper.getBeanByType(LoginInfoDto.class);

	@RequestMapping(value = "/queryOrder", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryInit(@RequestParam Map<String, String> queryParam) {
		// System.out.println("queryInitController");
		logger.info("============经销商撤单(小区)查询01===============");
		PageInfoDto pageInfoDto = dealerOrderAuditService.queryInit(queryParam);

		return pageInfoDto;

	}

	/**
	 * 经销商撤单查询（小区） 下载
	 */
	@RequestMapping(value = "/areaDownload/excel", method = RequestMethod.GET)
	public void findOrderDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============经销商撤单(区域)查询下载01===============");
		dealerOrderAuditService.findCancleOrderAuditDownload(queryParam, request, response);
	}

	/**
	 * 经销商撤单驳回(小区)
	 */
	@RequestMapping(value = "/queryReject", method = RequestMethod.PUT)
	@ResponseBody
	public void queryReject(@RequestBody @Valid K4OrderDTO k4OrderDTO) {
		// System.out.println("queryInitController");
		logger.info("============经销商撤单驳回(小区)===============");
		dealerOrderAuditService.modifyOderRejectSam(k4OrderDTO);

	}

	/**
	 * 经销商撤单（区域）通过
	 */
	@RequestMapping(value = "/queryPass", method = RequestMethod.PUT)
	@ResponseBody
	public void queryPass(@RequestBody @Valid K4OrderDTO k4OrderDTO) {
		// System.out.println("queryInitController");
		logger.info("============经销商撤单通过===============");
		// logger.debug("ids:" + k4OrderDTO.getIds());

		dealerOrderAuditService.modifyOderPass(k4OrderDTO);

	}

	/**
	 * 经销商撤单Query查询
	 * 
	 * @param queryParam
	 * @return
	 */

	@RequestMapping(value = "/queryAudit", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryAudit(@RequestParam Map<String, String> queryParam) {
		// System.out.println("queryInitController");
		logger.info("============经销商撤单Query查询===============");
		PageInfoDto pageInfoDto = dealerOrderAuditService.queryAuditList(queryParam);

		return pageInfoDto;

	}

	/**
	 * 小区查询下载
	 * 
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryAuditDownload/excel", method = RequestMethod.GET)
	@ResponseBody
	public void queryAuditDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		// System.out.println("queryInitController");
		logger.info("============经销商撤单Query下载===============");

		dealerOrderAuditService.findCancleOrderDownload(queryParam, request, response);

	}

}
