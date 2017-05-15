package com.yonyou.dms.vehicle.controller.k4Order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.k4Order.CancelOrderApplyDao;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.K4OrderDTO;
import com.yonyou.dms.vehicle.service.k4Order.CancelOrderApplyService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 *
 * 
 * @author liujiming
 * @date 2017年3月10日
 */
@Controller
@TxnConn
@RequestMapping("/CancelOrderApply")
public class CancelOrderApplyController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(CancelOrderApplyController.class);

	@Autowired
	private CancelOrderApplyDao cancleAppDao;

	@Autowired
	private CancelOrderApplyService cancleAppService;

	/**
	 * 撤单申请(经销商)查询
	 * 
	 * @param queryParam
	 *            查询条件
	 * @return pageInfoDto 查询结果
	 */
	@RequestMapping(value = "/cancleDealer/orderApply", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto cancelOrderApplyQuery(@RequestParam Map<String, String> queryParam) {
		// System.out.println("queryInitController");
		logger.info("============撤单申请(经销商)查询11==============");
		PageInfoDto pageInfoDto = cancleAppDao.getCancelOrderApplyList(queryParam);

		return pageInfoDto;

	}

	/**
	 * 撤单申请(经销商)下载
	 * 
	 * @param queryParam
	 *            查询条件
	 */
	@RequestMapping(value = "/cancleDealer/applyDownload", method = RequestMethod.GET)
	public void cancelOrderApplyQueryDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============撤单申请(经销商)下载11===============");
		cancleAppService.findCancelOrderApplyDownload(queryParam, request, response);

	}

	/**
	 * 撤单申请(经销商) 申请
	 */
	@RequestMapping(value = "/cancleDealer/applyPass", method = RequestMethod.PUT)
	@ResponseBody
	public void queryPass(@RequestBody @Valid K4OrderDTO k4OrderDTO) {
		// System.out.println("queryInitController");
		logger.info("============撤单多选申请(经销商)===============");

		cancleAppService.updateTmOrderPayChangeByVIN(k4OrderDTO);

	}

	/**
	 *  
	 */
	@RequestMapping(value = "/apply/{vin}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("vin") String vin) {
		logger.info("============撤单单个申请(经销商)11===============");
	}

}
