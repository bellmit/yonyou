package com.yonyou.dms.vehicle.controller.orderManager;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.vehicle.domains.DTO.orderManager.FurtherOrderRangeListDTO2;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.FurtherOrderRangeSettingListDTO;
import com.yonyou.dms.vehicle.service.orderManage.FurtherOrderRangeSettingService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 
 * @author lianxinglu
 *
 */
@Controller
@TxnConn
@RequestMapping("/furtherOrderRangeSetting")
public class FurtherOrderRangeSettingController {
	@Autowired
	private FurtherOrderRangeSettingService service;
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(FurtherOrderRangeSettingController.class);

	@RequestMapping(value = "/settingRange", method = RequestMethod.PUT)
	public ResponseEntity<FurtherOrderRangeListDTO2> settingRange(
			@RequestBody @Valid FurtherOrderRangeSettingListDTO paramList) {
		logger.info("============资源备注设定===============");
		service.settingRange(paramList);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@RequestMapping(value = "/queryInit", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryInit() {
		logger.info("============资源备注设定查询===============");

		return service.queryInit();

	}

}
