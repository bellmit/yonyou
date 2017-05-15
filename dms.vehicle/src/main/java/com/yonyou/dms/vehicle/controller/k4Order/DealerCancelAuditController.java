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
import com.yonyou.dms.vehicle.dao.k4Order.DealerCancelAuditDao;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.K4OrderDTO;
import com.yonyou.dms.vehicle.service.k4Order.DealerCancleAuditService;
import com.yonyou.dms.vehicle.service.k4Order.DealerOrderAuditService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 *
 * 
 * @author liujiming
 * @date 2017年2月24日
 */
@Controller
@TxnConn
@RequestMapping("/dealerCancelAudit")
public class DealerCancelAuditController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(DealerCancelAuditController.class);

	@Autowired
	private DealerCancelAuditDao deaCanDao;
	@Autowired
	private DealerCancleAuditService deaCanService;

	@Autowired
	private DealerOrderAuditService dealerOrderAuditService;

	/**
	 * 大区查询
	 * 
	 * @author liujiming
	 * @date 2017年2月24日
	 * @param queryParam
	 *            查询条件
	 * @return 查询结果
	 */
	@RequestMapping(value = "/queryOrder/list", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryOrder(@RequestParam Map<String, String> queryParam) {
		// System.out.println("queryInitController");
		logger.info("============经销商撤单查询===============");
		PageInfoDto pageInfoDto = deaCanDao.getDealerCanceAuditList(queryParam);

		return pageInfoDto;

	}

	/**
	 * 经销商撤单查询（大区） 下载
	 * 
	 * @param queryParam
	 *            查询条件
	 */
	@RequestMapping(value = "/queryDownload/excel", method = RequestMethod.GET)
	public void findOrderQueryDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============经销商撤单查询下载===============");
		deaCanService.findCancleAuditDownload(queryParam, request, response);
	}

	/**
	 * 经销商撤单审核查询（大区）
	 * 
	 * @param queryParam
	 *            查询条件
	 */
	@RequestMapping(value = "/queryOrderAudit/list", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryAudit(@RequestParam Map<String, String> queryParam) {
		// System.out.println("queryInitController");
		logger.info("============经销商撤单审核查询===============");
		PageInfoDto pageInfoDto = dealerOrderAuditService.queryInit1(queryParam);

		return pageInfoDto;

	}

	/**
	 * 经销商撤单审核（大区） 下载
	 * 
	 * @param queryParam
	 *            查询条件
	 */
	@RequestMapping(value = "/queryAuditDownload/excel", method = RequestMethod.GET)
	public void findOrderAuditDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============经销商撤单审核下载03===============");
		dealerOrderAuditService.findCancleOrderAuditDownload(queryParam, request, response);
	}

	/**
	 * 经销商撤单驳回
	 * 
	 * @param k4OrderDTO
	 *            参数
	 */
	@RequestMapping(value = "/queryReject", method = RequestMethod.PUT)
	@ResponseBody
	public void queryReject(@RequestBody @Valid K4OrderDTO k4OrderDTO) {
		// System.out.println("queryInitController");
		logger.info("============经销商撤单驳回03===============");
		dealerOrderAuditService.modifyOderReject(k4OrderDTO);

	}

	/**
	 * 经销商撤单通过
	 * 
	 * @param k4OrderDTO
	 *            参数
	 */
	@RequestMapping(value = "/queryPass11", method = RequestMethod.PUT)
	@ResponseBody
	public void queryPass(@RequestBody @Valid K4OrderDTO k4OrderDTO) {

		logger.info("============经销商撤单通过===============");
		dealerOrderAuditService.modifyOderPass11(k4OrderDTO);
	}

}
