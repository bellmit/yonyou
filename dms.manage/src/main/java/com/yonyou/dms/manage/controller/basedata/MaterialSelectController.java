package com.yonyou.dms.manage.controller.basedata;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.manage.service.basedata.vehicleModule.MaterialSelectService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;


/**
 * 物料下拉联动组件
 * @author xiawei
 * @date 2017年3月6日
 */

@Controller
@TxnConn
@RequestMapping("/basedata/material")
public class MaterialSelectController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(MaterialSelectController.class);
	
	@Autowired
	MaterialSelectService service;
	
	/**
     * 品牌查询
     * @author xiawei
     * @date 2017年3月6日
     * @return
     */
    @RequestMapping(value="/brand/{type}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getBrandList(@PathVariable(value = "type") Integer type){
    	logger.info("=====物料品牌加载=====");
    	List<Map> tenantMapping = service.getBrandList(type);
        return tenantMapping;
    }
    /**
     * 车系查询
     * @author xiawei
     * @date 2017年3月6日
     * @return
     */
    @RequestMapping(value="/series/{type}/{brand}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getSeriesList(@PathVariable(value = "type") Integer type,@PathVariable(value = "brand") String brand){
    	logger.info("=====物料车系联动加载=====");
    	List<Map> tenantMapping = service.getSeriesList(type,brand);
    	return tenantMapping;
    }
    
    /**
     * 车款查询
     * @author xiawei
     * @date 2017年3月6日
     * @return
     */
    @RequestMapping(value="/group/{type}/{series}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getGroupList(@PathVariable(value = "type") Integer type
    		,@PathVariable(value = "series") String series){
    	logger.info("=====物料车款联动加载=====");
    	List<Map> tenantMapping = service.getGroupList(type,series);
    	return tenantMapping;
    }
    
    /**
     * 年款查询
     * @author xiawei
     * @date 2017年3月6日
     * @return
     */
    @RequestMapping(value="/modelYear/{type}/{group}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getModelYearList(@PathVariable(value = "type") Integer type,@PathVariable(value = "group") String group){
    	logger.info("=====物料年款联动加载=====");
    	List<Map> tenantMapping = service.getModelYearList(type,group);
    	return tenantMapping;
    }
    
    /**
     * 颜色查询
     * @author xiawei
     * @date 2017年3月6日
     * @return
     */
    @RequestMapping(value="/color/{type}/{group}/{modelYear}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getColorList(@PathVariable(value = "type") Integer type,@PathVariable(value = "group") String group
    		,@PathVariable(value = "modelYear") String modelYear){
    	logger.info("=====物料颜色联动加载=====");
    	List<Map> tenantMapping = service.getColorList(type,group,modelYear);
    	return tenantMapping;
    }
    
    /**
     * 内饰查询
     * @author xiawei
     * @date 2017年3月6日
     * @return
     */
    @RequestMapping(value="/trim/{type}/{group}/{modelYear}/{color}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getTrimList(@PathVariable(value = "type") Integer type,@PathVariable(value = "group") String group
    		,@PathVariable(value = "modelYear") String modelYear,@PathVariable(value = "color") String color){
    	logger.info("=====物料内饰联动加载=====");
    	List<Map> tenantMapping = service.getTrimList(type,group,modelYear,color);
    	return tenantMapping;
    }
    
}
