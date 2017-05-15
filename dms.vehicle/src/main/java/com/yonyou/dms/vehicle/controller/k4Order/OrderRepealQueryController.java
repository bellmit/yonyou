package com.yonyou.dms.vehicle.controller.k4Order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.k4Order.OrderRepealQueryDao;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.K4OrderDTO;
import com.yonyou.dms.vehicle.service.k4Order.OrderRepealQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 *
 * 
 * @author liujiming
 * @date 2017年2月27日
 */
@Controller
@TxnConn
@RequestMapping("/orderRepealQuery")
public class OrderRepealQueryController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(OrderRepealQueryController.class);

	@Autowired
	private OrderRepealQueryDao orderReQuDao;

	@Autowired
	private OrderRepealQueryService orderReQuService;

	/**
	 * 
	 * @param queryParam
	 *            查询条件
	 * @return pageInfoDto 查询结果
	 */
	@RequestMapping(value = "/queryOrder/list", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryOrderRepeal(@RequestParam Map<String, String> queryParam) {
		// System.out.println("queryInitController");
		logger.info("============整车订单撤单查询05===============");
		PageInfoDto pageInfoDto = orderReQuDao.findOrderRepealQueryList(queryParam);

		return pageInfoDto;

	}

	/**
	 * 整车撤单查询 下载
	 */
	@RequestMapping(value = "/orderDownload/excel", method = RequestMethod.GET)
	public void findOrderRepealDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============整车订单撤单下载05===============");
		orderReQuService.findOrderRepealQueryDownload(queryParam, request, response);

	}

	/**
	 * 根据ID 获取订单信息
	 * 
	 * @author liujiming
	 * @date 2017年2月28日
	 * @param id
	 *            订单ID
	 * @return
	 */

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrderRepealById(@PathVariable(value = "id") Long id) {
		logger.info("============整车订单撤单明细05===============");
		Map<String, Object> map = orderReQuService.findOrderRepealById(id);
		return map;
	}

	/**
	 * 整车订单撤单通过
	 */
	@RequestMapping(value = "/queryPass", method = RequestMethod.PUT)
	@ResponseBody
	public void queryPass(@RequestBody @Valid K4OrderDTO k4OrderDTO) {
		// System.out.println("queryInitController");
		logger.info("============整车订单撤单通过05===============");
		logger.debug("orderIds:" + k4OrderDTO.getOrderIds());

		orderReQuService.queryPass(k4OrderDTO);

	}

}
