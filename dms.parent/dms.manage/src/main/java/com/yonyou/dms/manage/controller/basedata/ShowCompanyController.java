package com.yonyou.dms.manage.controller.basedata;

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
import com.yonyou.dms.manage.service.basedata.org.CompanyService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;


/**
 * 公司信息加载
 * @author 夏威
 * /basedata/company
 */
@Controller
@TxnConn
@RequestMapping("/basedata")
public class ShowCompanyController extends BaseController {
	
private static final Logger logger = LoggerFactory.getLogger(ShowCompanyController.class);
	
	@Autowired
	CompanyService service;
	
	/**
     * 弹出OEM公司弹出页面
     * @author xiawei
     * @date 2017年2月27日
     * @return
     */
    @RequestMapping(value="/dealerCompany",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto getCompanyList(@RequestParam Map<String, String> queryParams){
    	logger.info("=====公司信息查询=====");
    	PageInfoDto tenantMapping = service.getCompanyList(queryParams,2);
        return tenantMapping;
    }
    
	/**
     * 弹出OEM公司弹出页面
     * @author xiawei
     * @date 2017年2月27日
     * @return
     */
    @RequestMapping(value="/oemCompany",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto getOemCompanyList(@RequestParam Map<String, String> queryParams){
    	logger.info("=====公司信息查询=====");
    	PageInfoDto tenantMapping = service.getCompanyList(queryParams,1);
        return tenantMapping;
    }

}
