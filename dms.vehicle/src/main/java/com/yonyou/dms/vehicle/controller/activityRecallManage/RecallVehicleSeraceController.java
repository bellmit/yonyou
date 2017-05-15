package com.yonyou.dms.vehicle.controller.activityRecallManage;

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
import com.yonyou.dms.vehicle.domains.DTO.activityManage.ActivityManageDTO;
import com.yonyou.dms.vehicle.service.activityRecallManage.RecallVehicleSeraceService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* @author liujm
* @date 2017年4月22日
*/
@SuppressWarnings({ "rawtypes" })

@Controller
@TxnConn
@RequestMapping("/recallVehicleSerace")
public class RecallVehicleSeraceController {
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(RecallVehicleSeraceController.class);
		
	
	@Autowired
	private RecallVehicleSeraceService rvsService;
	
	/**
	 * 召回车辆查询Dlr	查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/Query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto recallVehicleSeraceQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============召回车辆查询Dlr 查询07==============");
    	PageInfoDto pageInfoDto = rvsService.recallVehicleSeraceQuery(queryParam);   	
        return pageInfoDto;               
    }
	/**
	 * 召回车辆查询Dlr	 下载
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public void recallVehicleSeraceDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============召回车辆查询 下载07===============");
    	rvsService.recallVehicleSeraceDownload(queryParam, request, response);
	}
	
	
	
	@RequestMapping(value="/QueryRecall/{VIN}/{RECALL_ID}",method = RequestMethod.GET)
    @ResponseBody
    public Map queryRecallDateil(@PathVariable("VIN") String vin,@PathVariable("RECALL_ID") String recallId,
    		@RequestParam Map<String, String> queryParam) {
    	logger.info("============召回车辆查询Dlr 查询活动07==============");
    	Map map = rvsService.queryRecallDateil(vin, recallId);   	
        return map;               
    }
	
	@RequestMapping(value="/QueryCustomer/{VIN}/{RECALL_ID}",method = RequestMethod.GET)
    @ResponseBody
    public Map queryCustomerDateil(@PathVariable("VIN") String vin,@PathVariable("RECALL_ID") String recallId,
    		@RequestParam Map<String, String> queryParam) {
    	logger.info("============召回车辆查询Dlr 查询车主07==============");
    	Map map = rvsService.queryCustomerDateil(vin, recallId);   	
        return map;               
    }
	@RequestMapping(value="/QueryVehicle/{VIN}/{RECALL_ID}",method = RequestMethod.GET)
    @ResponseBody
    public Map queryVehicleDateil(@PathVariable("VIN") String vin,@PathVariable("RECALL_ID") String recallId,
    		@RequestParam Map<String, String> queryParam) {
    	logger.info("============召回车辆查询Dlr 查询车辆07==============");
    	Map map = rvsService.queryVehicleDateil(vin, recallId);   	
        return map;               
    }
	/**
	 * 修改Date
	 * @param queryParam
	 */
	@RequestMapping(value="/update",method = RequestMethod.POST)
    @ResponseBody
    public void updateRecallTime(@RequestBody @Valid ActivityManageDTO amDto,
    		@RequestParam Map<String, String> queryParam) {
    	logger.info("============召回车辆查询Dlr 修改召回时间07==============");
    	rvsService.updateRecallTime(amDto);             
    }
}














