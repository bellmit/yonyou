/**
 * 
 */
package com.yonyou.dms.customer.controller.vehicleTransfer;

import java.util.HashMap;
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

import com.yonyou.dms.common.domains.DTO.customer.OwnerDTO;
import com.yonyou.dms.customer.service.vehicleTransfer.VehicleTransferService;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * @author sqh
 *
 */
@Controller
@TxnConn
@RequestMapping("/customer/transfer")
public class VehicleTransferController {

	@Autowired
	private VehicleTransferService vehicleTransferService;
	
	/**
	 * 根据车主编号回显查询车主信息
	 * 
	 * @author
	 * @param id
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/{vin}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryOwnerById(@PathVariable(value = "vin") String id) throws ServiceBizException {
		List<Map> result = vehicleTransferService.queryOwnerNoByid(id);
		return result.get(0);
	}
	
	/**
	 * 
	 *车辆过户
	 * 
	 * @param ownerNo
	 * @param uriCB
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping( method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Map> ModifyOwnerById(@RequestBody Map<String, String> queryParam,UriComponentsBuilder uriCB) {
		
		List<Map> list = vehicleTransferService.ModifyOwnerById(queryParam);
		MultiValueMap<String, String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/customer/transfer").buildAndExpand().toUriString());  
		return new ResponseEntity<Map>(list.get(0),headers, HttpStatus.CREATED);  
	}
}
