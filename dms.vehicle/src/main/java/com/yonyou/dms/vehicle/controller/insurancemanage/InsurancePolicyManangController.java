package com.yonyou.dms.vehicle.controller.insurancemanage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.CardCouponsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceCompanyExcelTempDcsDTO;
import com.yonyou.dms.vehicle.service.insurancemanage.InsurancePolicyManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 
 * @author zhiahongmiao 
 *
 */
@Controller
@TxnConn
@RequestMapping("/InsurancePolicyManage")
public class InsurancePolicyManangController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	InsurancePolicyManageService  insurancePolicyManangeService;
    /**
     * 查询
     */
    @RequestMapping(value="/Query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto InsurancePolicyManageQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("-----------------------查询-----------------------");
    	PageInfoDto pageInfoDto = insurancePolicyManangeService.InsurancePolicyManageQuery(queryParam);   	
        return pageInfoDto;               
    }
    /**
	 *下载
	 */
	@RequestMapping(value = "/Download", method = RequestMethod.GET)
    @ResponseBody
    public void insurancePolicyManangeDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("-----------------------下载-----------------------");
    	insurancePolicyManangeService.insurancePolicyManangeDownload(queryParam, request, response);
	}
	/**
     * 查询
     */
    @RequestMapping(value="/codeDescQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto codeDescQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("-----------------------查询维修类型-----------------------");
    	PageInfoDto pageInfoDto = insurancePolicyManangeService.codeDescQuery(queryParam);   	
        return pageInfoDto;               
    }
    /**r
     * 查询
     */
    @RequestMapping(value="/insurancecompanyQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto insurancecompanyQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("-----------------------查询保险公司-----------------------");
    	PageInfoDto pageInfoDto = insurancePolicyManangeService.insurancecompanyQuery(queryParam);   	
        return pageInfoDto;               
    }
    /**
	 * 新增(查询出相应数据进行修改)
	 */
	 @RequestMapping(value = "/Add",method = RequestMethod.POST)
	    public ResponseEntity<CardCouponsDTO> InsurancePolicyManageAdd(@RequestBody CardCouponsDTO ccDTO, UriComponentsBuilder uriCB) {
	    	logger.info("-----------------------新增-------------------------------");
	    	Long id = insurancePolicyManangeService.Add(ccDTO);
	        MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/insurancecompanyQuery/Add").buildAndExpand(id).toUriString());
	        return new ResponseEntity<CardCouponsDTO>(ccDTO, headers, HttpStatus.CREATED);

	    }
}
