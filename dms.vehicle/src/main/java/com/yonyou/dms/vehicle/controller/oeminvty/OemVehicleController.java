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
 * 车场库存管理
 * 
 * @author 廉兴鲁
 *
 */
@Controller
@TxnConn
@RequestMapping("/inventoryManage")
@ResponseBody
public class OemVehicleController {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(OemVehicleController.class);
	@Autowired
	private OemVehicleService oemVehicleService;

	@RequestMapping(value = "/oemVehicleDetailedQuery", method = RequestMethod.GET)

	@ResponseBody
	public PageInfoDto oemVehicleDetailedQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============OEM车辆综合查询===============");
		PageInfoDto pageInfoDto = oemVehicleService.oemVehicleDetailedQuery(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/areaVehicleDetailedQuery", method = RequestMethod.GET)

	@ResponseBody
	public PageInfoDto areaVehicleDetailedQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============area车辆综合查询===============");
		PageInfoDto pageInfoDto = oemVehicleService.oemVehicleDetailedQuery(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============oem车辆综合查询下载===============");
		oemVehicleService.download(queryParam, response, request);

	}

	@RequestMapping(value = "/areaVehicleDownload", method = RequestMethod.GET)
	public void areaVehicleDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============arae车辆综合查询下载===============");
		oemVehicleService.areaVehicleDownload(queryParam, response, request);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> oemVehicleDetailed(@PathVariable(value = "id") Long id) {
		logger.info("============车辆综合详细查询===============");
		Map<String, Object> map = oemVehicleService.oemVehicleVinDetailQuery(id);

		return map;
	}

	@RequestMapping(value = "/oemVehicleVinDetailQuery/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto oemVehicleDetailed(@RequestParam Map<String, String> queryParam,
			@PathVariable(value = "id") Long id) {
		logger.info("============车辆综合详细查询--变更日志===============");
		queryParam.put("id", id.toString());
		PageInfoDto pageInfoDto = oemVehicleService.oemVehicleDetailed(queryParam);
		return pageInfoDto;
	}

	// inventoryManage/downloadDe
	@RequestMapping(value = "/downloadDe/{id}", method = RequestMethod.GET)
	public void downloadDetailsl(@RequestParam Map<String, String> queryParam, @PathVariable(value = "id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("============车辆综合详细查询--变更日志下载===============");
		queryParam.put("id", id.toString());
		oemVehicleService.downloadDetailsl(queryParam, response, request);

	}
}
