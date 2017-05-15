package com.yonyou.dms.vehicle.controller.oeminvty;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.oeminvty.InventoryStatusService;
import com.yonyou.f4.mvc.annotation.TxnConn;

@Controller
@TxnConn
@RequestMapping("/inventoryManage")
@ResponseBody
public class InventoyStatusController {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(InventoyStatusController.class);
	@Autowired
	private InventoryStatusService service;

	@RequestMapping(value = "/inventoryStatusQueryCollect", method = RequestMethod.GET)

	@ResponseBody
	public PageInfoDto inventoryStatusQueryCollect(@RequestParam Map<String, String> queryParam) {
		logger.info("============车厂库存汇总查询===============");
		PageInfoDto pageInfoDto = service.inventoryStatusQueryCollect(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/inventoryStatusQueryDetails", method = RequestMethod.GET)

	@ResponseBody
	public PageInfoDto inventoryStatusQueryDetails(@RequestParam Map<String, String> queryParam) {
		logger.info("============车厂库明细查询===============");
		PageInfoDto pageInfoDto = service.inventoryStatusQueryDetails(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/downloadDetailsl", method = RequestMethod.GET)
	public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============车厂库明细下载===============");
		service.downloadDetailsl(queryParam, response, request);

	}

	/**
	 * 联动查询
	 * 
	 */

	@RequestMapping(value = "/findnodeStatus/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findById(@PathVariable(value = "id") Long id) {
		logger.info("============车辆节点联动查询===============");
		List<Map> map = service.findnodeStatus(id);
		return map;
	}
}
