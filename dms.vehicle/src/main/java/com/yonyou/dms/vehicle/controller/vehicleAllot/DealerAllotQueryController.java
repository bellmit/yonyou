package com.yonyou.dms.vehicle.controller.vehicleAllot;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.vehicleAllot.DealerAllotQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 车辆调拨查询（经销商）
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/vehicleAllotMange/dealerQuery")
public class DealerAllotQueryController {
	
	@Autowired
	private DealerAllotQueryService allotService;
	
	private static final Logger logger = LoggerFactory.getLogger(DealerAllotQueryController.class);
	
	/**
	 * 首页初始化
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String, String> param){
		logger.info("==================车辆调出申请查询（经销商）=================");
		PageInfoDto dto = allotService.search(param);
		return dto;
		
	}
	

}
