package com.yonyou.dms.vehicle.controller.insurancereport;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.insurancereport.PolicyDetailManageDlrService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 保单明细查询DLR
 * @author zhiahongmiao 
 *
 */
@Controller
@TxnConn		  
@RequestMapping("/PolicyDetailManageDlr")
public class PolilcyDetailManageControllerDlr {
	@Autowired
	PolicyDetailManageDlrService  policyDetailManageService;
    /**
     * 关联车型
     */
    @RequestMapping(value="{series}/getSeries",method = RequestMethod.GET)
  	@ResponseBody
  	public List<Map> getModel(@PathVariable(value = "series") Long series) {
  		 return policyDetailManageService.getModel(series);
  	}
    /**
     * 关联车款
     */
    @RequestMapping(value="{model}/getGroup",method = RequestMethod.GET)
  	@ResponseBody
  	public List<Map> getGroup(@PathVariable(value = "model") Long model) {
  		 return policyDetailManageService.getGroup(model);
  	}
	
    /**
     * 查询
     */
    @RequestMapping(value="/Query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto PolilcyDetailManageQuery(@RequestParam Map<String, String> queryParam) {
    	PageInfoDto pageInfoDto = policyDetailManageService.PolilcyDetailManageQuery(queryParam);   	
        return pageInfoDto;               
    }
    /**
	 *下载
	 */
	@RequestMapping(value = "/Download", method = RequestMethod.GET)
    @ResponseBody
    public void PolilcyDetailManageDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	policyDetailManageService.PolilcyDetailManageDownload(queryParam, request, response);
	}
	/**
	 * 保险公司简称
	 */
	 @RequestMapping(value="/getcompanyCode",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> getcompanyCode(@RequestParam Map<String, String> queryParams){
	    	List<Map> tenantMapping = policyDetailManageService.getcompanyCode(queryParams);
	        return tenantMapping;
	    }
	 /**
	 * 保种
	 */
		 @RequestMapping(value="/getsortCode",method = RequestMethod.GET)
		    @ResponseBody
		    public List<Map> getsortCode(@RequestParam Map<String, String> queryParams){
		    	List<Map> tenantMapping = policyDetailManageService.getsortCode(queryParams);
		        return tenantMapping;
		    }
}
