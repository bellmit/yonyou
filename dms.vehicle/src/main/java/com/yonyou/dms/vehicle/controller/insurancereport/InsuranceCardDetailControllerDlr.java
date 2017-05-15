package com.yonyou.dms.vehicle.controller.insurancereport;

import java.util.List;
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
import com.yonyou.dms.vehicle.service.insurancereport.InsuranceCardDetailDlrService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 保单明细查询
 * @author zhiahongmiao 
 *
 */
@Controller
@TxnConn		  
@RequestMapping("/InsuranceCardDetailDlr")
public class InsuranceCardDetailControllerDlr {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	InsuranceCardDetailDlrService  insuranceCardDetailService;
     /**
     * 查询
     */
    @RequestMapping(value="/Query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto InsuranceCardDetailQuery(@RequestParam Map<String, String> queryParam) {
    	PageInfoDto pageInfoDto = insuranceCardDetailService.InsuranceCardDetailQuery(queryParam);   	
        return pageInfoDto;               
    }
    /**
	 *下载
	 */
	@RequestMapping(value = "/Download", method = RequestMethod.GET)
    @ResponseBody
    public void InsuranceCardDetailDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		insuranceCardDetailService.InsuranceCardDetailDownload(queryParam, request, response);
	}
	
	 /**
	 * 卡券类型名称
	 */
		 @RequestMapping(value="/getvoucherName",method = RequestMethod.GET)
		    @ResponseBody
		    public List<Map> getsortCode(@RequestParam Map<String, String> queryParams){
		    	List<Map> tenantMapping = insuranceCardDetailService.getvoucherName(queryParams);
		        return tenantMapping;
		    }
}
