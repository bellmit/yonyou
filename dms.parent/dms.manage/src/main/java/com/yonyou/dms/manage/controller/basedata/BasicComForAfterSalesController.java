/**
 * 
 */
package com.yonyou.dms.manage.controller.basedata;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.manage.service.basedata.BasicComForAfterSalesService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * @author yangjie
 *
 */
@Controller
@TxnConn
@RequestMapping("/basedata/BasicParametersAfterSales")
@SuppressWarnings("rawtypes")
public class BasicComForAfterSalesController extends BaseController{

	@Autowired
	private BasicComForAfterSalesService basicComService;
	
	/**
	 * 维修页面
	 * @return
	 */
	@RequestMapping(value="/findAllRepair",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> findAllRepair(){
		return basicComService.findAllRepair();
	}
	
	/**
	 * 配件页面
	 * @return
	 */
	@RequestMapping(value="/findAllPart",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> findAllPart(){
		return basicComService.findAllPart();
	}
	
	/**
	 * 报表页面
	 * @return
	 */
	@RequestMapping(value="/findAllReport",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> findAllReport(){
		return basicComService.findAllReport();
	}
	
	/**
	 * 积分页面
	 * @return
	 */
	@RequestMapping(value="/findAllIntegral",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> findAllIntegral(){
		return basicComService.findAllIntegral();
	}
	
	/**
	 * 下拉框查询仓库
	 * @return
	 */
	@RequestMapping(value="/findAllStroageCode",method=RequestMethod.GET)
	@ResponseBody
	public List<Map> findStorageCode(){
		return basicComService.findStorageCode();
	}
	
	/**
	 * 下拉框查询发票类型
	 * @return
	 */
	@RequestMapping(value="/findInvoiceCode",method=RequestMethod.GET)
	@ResponseBody
	public List<Map> findInvoiceCode(){
		return basicComService.findInvoiceCode();
	}
	
	/**
	 * 下拉框查询付款方式
	 * @return
	 */
	@RequestMapping(value="/findPaymentCode",method=RequestMethod.GET)
	@ResponseBody
	public List<Map> findPaymentCode(){
		return basicComService.findPaymentCode();
	}
	
	/**
	 * 下拉框查询收费类别
	 * @return
	 */
	@RequestMapping(value="/findChargeCode",method=RequestMethod.GET)
	@ResponseBody
	public List<Map> findChargeCode(){
		return basicComService.findChargeCode();
	}
	
	/**
	 * 修改基本参数方法
	 * @param param
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<String> updateBasicParam(@RequestBody Map<String, String> param,UriComponentsBuilder uriCB){
		basicComService.updateBasicParam(param);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/basedata/BasicParametersAfterSales/findAllRepair").buildAndExpand().toUriString());  
		return new ResponseEntity<String>(headers, HttpStatus.CREATED); 
	}
}
