package com.yonyou.dms.vehicle.controller.vehicleAllot;

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
import com.yonyou.dms.vehicle.service.vehicleAllot.VehicleAllotQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;


/**
 * 车辆调拨查询
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/vehicleAllotMange/query")
public class VehicleAllotQueryController {
	
	@Autowired
	private VehicleAllotQueryService queryService;
	
	private static final Logger logger = LoggerFactory.getLogger(DealerAllotApplyController.class);
	
	/**
	 * 首页初始化
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String, String> param){
		logger.info("==================车辆调拨查询================");
		PageInfoDto pageInfoDto = queryService.searchVehicleAllotQuery(param);
		return pageInfoDto;		
	}
	
	/**
	 * 详细查询
	 * @param transferId
	 * @return
	 */
	@RequestMapping(value = "/{transferId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findDetailById(@PathVariable String transferId){
		logger.info("==================车辆调拨查询详细================");
		Map<String, Object> map = queryService.findDetailById(transferId);
		return map;   	
    }
	
	/**
	 * excel导出
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void exportQueryInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response){
		logger.info("==================车辆调拨查询导出================");
		queryService.exportQueryInfo(queryParam,request,response);
	}

}
