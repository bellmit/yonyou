package com.yonyou.dms.customer.controller.customerManage;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.customer.domains.DTO.customerManage.SalesGatheringDTO;
import com.yonyou.dms.customer.service.customerManage.SalesGatheringService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/customerManage/salesGathering")
public class SalesGatheringContoller extends BaseController{
	
	@Autowired
    private SalesGatheringService salesgatheringservice;

	    @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryCusInfo(@RequestParam Map<String, String> queryParam) {
	        PageInfoDto pageInfoDto = salesgatheringservice.queryCusInfo(queryParam);
	        return pageInfoDto;
	    }
	    
		
	     @RequestMapping(value = "/{id}",method = RequestMethod.GET)
	     @ResponseBody
	     public List<Map> salesGatheringbyId(@PathVariable(value = "id") String id) {
	         List<Map> list = salesgatheringservice.salesGatheringbyId(id);
	         return list; 
	     }
	     
	     @RequestMapping(value = "/amount/{id}",method = RequestMethod.GET)
	     @ResponseBody
	     public List<Map> salesAmountbyId(@PathVariable(value = "id") String id) {
	         List<Map> list = salesgatheringservice.salesAmountbyId(id);
	         return list; 
	     }
	     
	     
	     @RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
	     @ResponseBody
	     public Map salesEditGatheringbyId(@PathVariable(value = "id") String id) {
	         Map map = salesgatheringservice.salesEditGatheringbyId(id);
	         return map; 
	     }
	     
	     @RequestMapping(method = RequestMethod.POST)
	     @ResponseBody
	     public ResponseEntity<SalesGatheringDTO> addSalesGathering(@RequestBody @Valid SalesGatheringDTO salesGatheringDto,
	                                                            UriComponentsBuilder uriCB) {
	    	 salesgatheringservice.addSalesGathering(salesGatheringDto);
	         MultiValueMap<String, String> headers = new HttpHeaders();
	         headers.set("Location", uriCB.path("/customerManage/salesGathering").buildAndExpand().toUriString());
	         return new ResponseEntity<SalesGatheringDTO>(salesGatheringDto, headers, HttpStatus.CREATED);
	     }
	     
	     @RequestMapping(value = "/{cus_no}", method = RequestMethod.PUT)
	     public ResponseEntity<Map<String, Object>> modifyRetainCusVehicleInfo(@PathVariable("cus_no") String cus_no,
	                                                                              @RequestBody @Valid SalesGatheringDTO salesGatheringDto,
	                                                                              UriComponentsBuilder uriCB) {
	    	 
	         Map<String, Object> tepomap = salesgatheringservice.editSalesGathering(cus_no, salesGatheringDto,null);
	         MultiValueMap<String, String> headers = new HttpHeaders();
	         headers.set("Location", uriCB.path("/customerManage/salesGathering").buildAndExpand(cus_no).toUriString());
	         return new ResponseEntity<Map<String, Object>>(tepomap, headers, HttpStatus.CREATED);
	     }
	     
	     @SuppressWarnings("rawtypes")
	     @RequestMapping(value = "/{orgCode}/qryPayTypeCode", method = RequestMethod.GET)
	     @ResponseBody
	     public List<Map> qryPayTypeCode(@PathVariable String orgCode) {
	         List<Map> list = salesgatheringservice.qryPayTypeCode(orgCode);
	         return list;
	     }
}
