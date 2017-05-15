package com.yonyou.dms.vehicle.controller.insurancereport;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceSortDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceSortExcelErrorDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.threePack.ForecastImportDto;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceSortDcsPO;
import com.yonyou.dms.vehicle.service.afterSales.warranty.MvsFamilyMaintainService;
import com.yonyou.dms.vehicle.service.insurancemanage.InsuranceSortManangeService;
import com.yonyou.dms.vehicle.service.insurancereport.PolicyDetailManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 保单明细查询
 * @author zhiahongmiao 
 *
 */
@Controller
@TxnConn		  
@RequestMapping("/PolicyDetailManage")
public class PolilcyDetailManageController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	PolicyDetailManageService  policyDetailManageService;
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
