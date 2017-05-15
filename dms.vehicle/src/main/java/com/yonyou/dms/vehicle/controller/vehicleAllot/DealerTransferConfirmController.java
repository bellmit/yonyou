package com.yonyou.dms.vehicle.controller.vehicleAllot;

import java.util.Map;

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
import com.yonyou.dms.vehicle.service.vehicleAllot.DealerTransferConfirmService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 车辆调入确认
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/vehicleAllotMange/dealerTransfer")
public class DealerTransferConfirmController {
	
	@Autowired
	private DealerTransferConfirmService transService;
	
	private static final Logger logger = LoggerFactory.getLogger(DealerAllotApplyController.class);
	
	/**
	 * 首页初始化
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String,String> param){
		logger.info("==================车辆调入确认（经销商）================");
		PageInfoDto dto = transService.search(param);
		return dto;		
	}
	
	/**
	 * 入库
	 * @param transferId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/vehicleConfirm/{transferId}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> vehicleConfirm(@PathVariable String transferId) throws Exception{
		logger.info("==================车辆调入确认入库（经销商）================");
		return transService.vehicleConfirm(transferId);
		
	}

}
