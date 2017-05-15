package com.yonyou.dms.vehicle.controller.afterSales.custcomVehicle;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.afterSales.custcomVehicle.VehicleWarrantyHisQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 车辆保修历史查询
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/vehicleWarrantyHis")
public class VehicleWarrantyHisQueryController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	VehicleWarrantyHisQueryService  vehicleWarrantyHisQueryService;
	/**
	 * 查询
	 */
	@RequestMapping(value="/vehicleWarrantyHisSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryVehicleWarrantyHis(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============车辆保修历史查询=============");
        PageInfoDto pageInfoDto =vehicleWarrantyHisQueryService.VehicleWarrantyHisQuery(queryParam);
        return pageInfoDto;  
    }
	/**
	 * 车辆保修历史下载
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============ 车辆保修历史下载===============");
		vehicleWarrantyHisQueryService.download(queryParam, response, request);
	}
	
	/**
	 * 经销商车辆保修历史下载
	 */
	@RequestMapping(value = "/download2", method = RequestMethod.GET)
	public void download2(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============经销商车辆保修历史下载===============");
		vehicleWarrantyHisQueryService.download2(queryParam, response, request);
	}
	
	
}
