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
import com.yonyou.dms.vehicle.service.vehicleAllot.DealerAllotApplyService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 车辆调出申请（经销商）
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/vehicleAllotMange/dealerApply")
public class DealerAllotApplyController {
	
	@Autowired
	private DealerAllotApplyService applyService;
	
	private static final Logger logger = LoggerFactory.getLogger(DealerAllotApplyController.class);
	
	/**
	 * 首页初始化
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String, String> param){
		logger.info("==================车辆调出申请申请================");
		PageInfoDto dto = applyService.search(param);
		return dto;
	}
	
	/**
	 * 申请前对经销撒商代码进行校验
	 * @param dealerCode
	 * @return
	 */
	@RequestMapping(value = "/checkDealer/{dealerCode}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> checkDealer(@PathVariable("dealerCode") String dealerCode){
		logger.info("==================车辆调出申请代码校验================");
        Map<String, String> result = applyService.checkDealer(dealerCode);
        return result;
		
	}
	
	/**
	 * 经销商详细页面
	 * @param dealerCode
	 * @return
	 */
	@RequestMapping(value = "/{dealerCode}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getDealerResult(@PathVariable("dealerCode") String dealerCode){
		logger.info("==================车辆调出申请详细返回================");
		return applyService.getDealerResult(dealerCode);
		
	}
	
	/**
	 * 申请
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/vehicleTransfersApply",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> vehicleTransfersApply(@RequestParam Map<String,String> param ){
		logger.info("==================车辆调出申请================");
		String inDealerCode = param.get("inDealerCode");
		String reason = param.get("reason");
		String vehicleId = param.get("vehicleId");
		return applyService.vehicleTransfersApply(inDealerCode, reason,vehicleId);	
	}


}
