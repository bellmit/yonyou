package com.yonyou.dms.vehicle.controller.afterSales.maintenance;
import java.util.Map;

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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.maintenance.TmWxReserverItemDefDTO;
import com.yonyou.dms.vehicle.service.afterSales.maintenance.DealerMakeAnPointmentService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 微信预约时段空闲度设定
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/dealerMakeAnPointment")
public class DealerMakeAnPointmentController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	DealerMakeAnPointmentService  dealerMakeAnPointmentService;
	
	/**
	 * 微信预约时段空闲度设定
	 */
	@RequestMapping(value="/dealerMakeAnPointmentSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryVehicleWarrantyHis(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============微信预约时段空闲度设定=============");
        PageInfoDto pageInfoDto =dealerMakeAnPointmentService.DealerMakeAnPointmentQuery(queryParam);
        return pageInfoDto;  
    }
	/**
	 * 微信预约时段空闲度设定更新
	 */
	 @RequestMapping(value = "/edit", method = RequestMethod.PUT)
	    @ResponseBody
	    public ResponseEntity<TmWxReserverItemDefDTO> edit(@RequestBody TmWxReserverItemDefDTO dto,UriComponentsBuilder uriCB){
	    	logger.info("===== 微信预约时段空闲度设定更新=====");
	    	dealerMakeAnPointmentService.edit(dto);
	    	MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/dealerMakeAnPointment/edit").buildAndExpand().toUriString());
	        return new ResponseEntity<TmWxReserverItemDefDTO>(headers, HttpStatus.CREATED);  	
	    }
	
	
	
	
	
}
