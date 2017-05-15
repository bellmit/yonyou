package com.yonyou.dms.vehicle.controller.afterSales.custcomVehicle;

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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.afterSales.custcomVehicle.CustcomVehicleQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 客户车辆资料查询
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/custcomVehicleZiLiao")
public class CustcomVehicleQueryController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	CustcomVehicleQueryService  custcomVehicleQueryService;
	/**
	 * 客户车辆资料查询
	 */
	@RequestMapping(value="/custcomVehicleZiLiaoSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryVehicleZiLiaoSearch(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============客户车辆资料查询=============");
        PageInfoDto pageInfoDto =custcomVehicleQueryService.VehicleZiLiaoQuery(queryParam);
        return pageInfoDto;  
    }
	/**
	 * 	通过id进行明细查询车辆信息
	 */
    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getVehicleById(@PathVariable(value = "id") Long id){
    	logger.info("=====通过id进行明细查询车辆信息=====");
    	Map m=custcomVehicleQueryService.getVehicle(id);
        return m;
    }
	/**
	 * 通过id进行明细查询客户信息
	 */
    @RequestMapping(value = "/getCustomer/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getCustomerById(@PathVariable(value = "id") Long id){
    	logger.info("=====通过id进行明细查询客户信息=====");
    	Map m=custcomVehicleQueryService.getCustomer(id);
        return m;
    }
	/**
	 * 通过id进行明细查询联系人信息
	 */
    @RequestMapping(value = "/getPeople/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getPeopleById(@PathVariable(value = "id") Long id){
    	logger.info("=====通过id进行明细查询联系人信息=====");
    	Map m=custcomVehicleQueryService.getPeople(id);
        return m;
    }
	/**
	 * 通过id进行查询二手车信息
	 */
    @RequestMapping(value = "/getCheXinxi/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getCheXinxiById(@PathVariable(value = "id") Long id){
    	logger.info("=====通过id进行查询二手车信息=====");
    	Map m=custcomVehicleQueryService.getCheXinXi(id);
        return m;
    }
}
