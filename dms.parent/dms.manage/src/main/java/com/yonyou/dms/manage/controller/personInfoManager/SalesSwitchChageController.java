package com.yonyou.dms.manage.controller.personInfoManager;


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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.service.basedata.personInfoManager.SalesSwitchChageService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/salesSwitchChage")
/**
 *零售开关设定
 * @author Administrator
 *
 */
public class SalesSwitchChageController extends BaseController{
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(SalesSwitchChageController.class);
	@Autowired
	SalesSwitchChageService   service;
	
	/**
	 * 经销商信息查询
	 * @param queryParam
	 * @return
	 */
    @RequestMapping(value="/searchDealer",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryUsers(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============经销商信息查询=============");
        PageInfoDto pageInfoDto = service.salesSwitchChangeQuery(queryParam);
        return pageInfoDto;  
    }
    
    /**
     * 销售大区查询
     * @param queryParams
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
     * 销售小区查询
     * @param bigorgid
     * @param queryParams
     * @return
     */
    @RequestMapping(value="/{bigorgid}/smallOrg",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getSmallOrg(@PathVariable String bigorgid,@RequestParam Map<String, String> queryParams){
    	logger.info("=====销售小区查询=====");
    	List<Map> tenantMapping = service.getSmallOrg(bigorgid,queryParams);
    	return tenantMapping;
    }
    
    /**
     * 获取开关开启经销商
     * @return
     */
    @RequestMapping(value="/getOrgLeft",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getOrgLeft(){
    	logger.info("=====获取开关开启经销商=====");
    	List<Map> list = service.getOrgLeft();
		return list;    	
    }
    
    /**
     * 获取开关关闭经销商
     * @return
     */
    @RequestMapping(value="/getOrgRight",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getOrgRight(){
    	logger.info("=====获取开关关闭经销商=====");
    	List<Map> list = service.getOrgRight();
		return list; 
    }
    
    /**
     * 查询选中经销商
     * @param param
     * @return
     */
    @RequestMapping(value="/searchOrg",method = RequestMethod.GET)
    @ResponseBody
    public String searchOrg(@RequestParam Map<String,String> param){
    	logger.info("=====根据查询条件查询经销商=====");
    	String org = service.searchOrg(param);
		return org;
    }
	
    /**
     * 零售开关设定  保存
     * @param ids
     * @return
     */
    @RequestMapping(value="/saveChange/{ids}",method = RequestMethod.POST)
    @ResponseBody
    public boolean saveChange(@PathVariable String ids){
    	logger.info("=====零售开关设定  保存=====");
    	service.saveChange(ids);
		return true;    	
    }

}
