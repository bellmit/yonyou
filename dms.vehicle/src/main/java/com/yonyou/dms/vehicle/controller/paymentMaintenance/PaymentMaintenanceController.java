package com.yonyou.dms.vehicle.controller.paymentMaintenance;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.dealerPayment.TmDealerPaymentDTO;
import com.yonyou.dms.vehicle.service.paymentMaintenance.PaymentMaintenanceService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 经销商付款方式维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/paymentMaintenance")
public class PaymentMaintenanceController {
	@Autowired
	PaymentMaintenanceService  paymentMaintenanceService;
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	/**
	 * 经销商付款方式查询
	 * @param queryParam
	 * @return
	 */
	   @RequestMapping(value="/paymentSearch",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryPayment(@RequestParam Map<String, String> queryParam) {
	    	logger.info("==============经销商付款方式查询=============");
	        PageInfoDto pageInfoDto =paymentMaintenanceService.PaymentMaintenanceQuery(queryParam);
	        return pageInfoDto;  
	    }
		/**
		 * 通过id查询经销商付款方式
		 */
	    @RequestMapping(value = "/getPayment/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Map getPaymentById(@PathVariable(value = "id") String id,@RequestParam Map<String, String> queryParam){
	    	logger.info("===== 通过id查询经销商付款方式=====");
	    	Map paymentById =paymentMaintenanceService.getPaymentById(id);
			return paymentById;
	    }
		
	   
	   /**
	    * 修改经销商付款方式信息
	    */
	    @RequestMapping(value = "/modify/{id}", method = RequestMethod.PUT)
	    @ResponseBody
	    public ResponseEntity<TmDealerPaymentDTO> ModifyPayment(@PathVariable(value = "id") Long id,@RequestBody TmDealerPaymentDTO ptdto,UriComponentsBuilder uriCB){
	    	logger.info("=====修改经销商付款方式信息=====");
	    	paymentMaintenanceService.modifyDealerPayment(id, ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        headers.set("Location", uriCB.path("/modify/{id}").buildAndExpand(id).toUriString());  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }
	    /**
	     * 添加经销商付款方式
	     */
	    @RequestMapping(value ="/addDealerPayment",method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseEntity<TmDealerPaymentDTO> addDealerPayment(@RequestBody TmDealerPaymentDTO ptdto,UriComponentsBuilder uriCB){
	    	logger.info("===== 添加经销商付款方式=====");
	    	Long id =paymentMaintenanceService.addDealerPayment(ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        headers.set("Location", uriCB.path("/paymentMaintenance/addDealerPayment").buildAndExpand(id).toUriString());  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }	    
	    
}
