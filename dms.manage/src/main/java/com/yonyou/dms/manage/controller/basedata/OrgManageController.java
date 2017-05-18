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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.manage.service.basedata.org.OrgManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;


/**
 * 经销商弹出多选页面
 * @author xiawei
 * @date 2016年7月15日
 */

@Controller
@TxnConn
@RequestMapping("/basedata/orgManage")
public class OrgManageController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(SearchDealersController.class);
	
	@Autowired
	OrgManageService service;
	
	/**
     * 弹出经销商多选界面
     * @author xiawei
     * @date 2017年2月20日
     * @return
     */
    @RequestMapping(value="/bigOrg",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getBigOrg(@RequestParam Map<String, String> queryParams){
    	logger.info("=====销售大区查询=====");
    	List<Map> tenantMapping = service.getBigOrg(queryParams);
        return tenantMapping;
    }
    /**
     * 弹出经销商多选界面
     * @author xiawei
     * @date 2017年2月20日
     * @return
     */
    @RequestMapping(value="/{bigorgid}/smallOrg",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getSmallOrg(@PathVariable String bigorgid,@RequestParam Map<String, String> queryParams){
    	logger.info("=====销售小区查询=====");
    	List<Map> tenantMapping = service.getSmallOrg(bigorgid,queryParams);
    	return tenantMapping;
    }
}
