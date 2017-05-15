package com.yonyou.dms.vehicle.controller.oeminvty;

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
import com.yonyou.dms.vehicle.service.oeminvty.OemVehicleService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * JV车场库存管理
 * 
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/k4InventoryManage")
@ResponseBody
public class OVehicleController {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(OVehicleController.class);
	@Autowired
	private OemVehicleService oemVehicleService;

	@RequestMapping(value = "/oemVehicleQuery", method = RequestMethod.GET)

	@ResponseBody
	public PageInfoDto oemVehicleQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============生产订单跟踪查询===============");
		PageInfoDto pageInfoDto = oemVehicleService.oemVehicleQuery(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/downLoad", method = RequestMethod.GET)
	public void oemVehicleQueryDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============生产订跟踪下载===============");
		oemVehicleService.oemVehicleQueryDownload(queryParam, response, request);

	}

	@RequestMapping(value = "/vehicleDetailQuery", method = RequestMethod.GET)

	@ResponseBody
	public PageInfoDto vehicleDetailQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============详细车籍查询===============");
		PageInfoDto pageInfoDto = oemVehicleService.vehicleDetailQuery(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/downloadInfo", method = RequestMethod.GET)
	public void doDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============详细车籍下载===============");
		oemVehicleService.doDownload(queryParam, response, request);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> doQueryDetail(@PathVariable(value = "id") Long id) {
		logger.info("============车辆综合详细查询===============");
		Map<String, Object> map = oemVehicleService.doQueryDetail(id);

		return map;
	}

	@RequestMapping(value = "/vehicleNodeChangeQuery/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto vehicleNodeChangeQuery(@PathVariable(value = "id") Long id) {
		logger.info("============车辆综合详细查询--变更日志===============");
		PageInfoDto map = oemVehicleService.vehicleNodeChangeQuery(id);

		return map;
	}

	@RequestMapping(value = "/doQuery", method = RequestMethod.GET)

	@ResponseBody
	public PageInfoDto doQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============车厂库存查询===============");
		PageInfoDto pageInfoDto = oemVehicleService.doQuery(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/DepotInventoryQuery/doDownload", method = RequestMethod.GET)
	public void depotInventorydoDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============车厂库存查询下载===============");
		oemVehicleService.depotInventorydoDownload(queryParam, response, request);

	}
}
