package com.yonyou.dms.vehicle.controller.orderManager;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.StockResourceDTO;
import com.yonyou.dms.vehicle.service.orderManage.StockResourceService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 现货车辆预定
 * 
 * @author 廉兴鲁
 * @date 2016年2月15日
 */
@Controller
@TxnConn
@RequestMapping("/stockResource")
public class StockResourceController {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(StockResourceController.class);
	@Autowired
	private StockResourceService stockResourceService;

	@RequestMapping(value = "/stockResourceQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto stockResourceQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============现货车辆查询===============");
		PageInfoDto pageInfoDto = stockResourceService.stockResourceQuery(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/findBandResource/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findStockResource(@PathVariable(value = "id") Long id) {
		logger.info("============现货车辆查询预定车辆回显===============");
		Map<String, Object> map = stockResourceService.findStockResourceById(id);
		return map;
	}

	@RequestMapping(value = "/findBandResource", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findStockRe() {
		logger.info("============现货车辆查询预定车辆回显===============");
		Map<String, Object> map = stockResourceService.findStockResource();
		return map;
	}

	@RequestMapping(value = "/stockResourceSubmit", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<StockResourceDTO> stockResourceSubmit(@RequestBody @Valid StockResourceDTO stockPO) {
		logger.info("============现货车辆预定===============");
		stockResourceService.doSubmit(stockPO);
		// MultiValueMap<String, String> headers = new HttpHeaders();
		//
		// headers.set("Location",
		// uriCB.path("/stockResource/stockResourceSubmit").buildAndExpand(0).toUriString());
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@RequestMapping(value = "/stockSubmit", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<StockResourceDTO> stockSubmit(@RequestBody @Valid StockResourceDTO stockPO) {
		logger.info("============现货车辆预定--批量操作===============");
		stockResourceService.doStockSubmit(stockPO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
