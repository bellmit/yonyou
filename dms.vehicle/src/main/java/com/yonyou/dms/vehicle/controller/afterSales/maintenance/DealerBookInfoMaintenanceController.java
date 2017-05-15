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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.maintenance.TmWxReserveSpecialistDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.maintenance.TmWxReserveSpecialistPO;
import com.yonyou.dms.vehicle.service.afterSales.maintenance.DealerBookInfoMaintenanceService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 经销商微信预约专员维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/dealerBookInfoMaintenance")
public class DealerBookInfoMaintenanceController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	DealerBookInfoMaintenanceService  dealerBookInfoMaintenanceService;
	/**
	 * 经销商微信预约专员查询
	 */
	@RequestMapping(value="/dealerMaintenanceSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryVehicleWarrantyHis(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============经销商微信预约专员查询=============");
        PageInfoDto pageInfoDto =dealerBookInfoMaintenanceService.DealerBookInfoMaintenanceQuery(queryParam);
        return pageInfoDto;  
    }
	
	
	/**
	 * 经销商微信预约专员新增
	 */
	 @RequestMapping(value ="/addMaintenanceDealer",method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseEntity<TmWxReserveSpecialistDTO> addDealerPayment(@RequestBody TmWxReserveSpecialistDTO ptdto,UriComponentsBuilder uriCB){
	    	logger.info("===== 经销商微信预约专员新增=====");
	    	Long id =dealerBookInfoMaintenanceService.addMaintenanceDealer(ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        headers.set("Location", uriCB.path("/dealerBookInfoMaintenance/addMaintenance").buildAndExpand(id).toUriString());  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }	
	 
	
	/**
	 * 经销商微信预约专员修改信息
	 */
	 @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	    @ResponseBody
	    public ResponseEntity<TmWxReserveSpecialistDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TmWxReserveSpecialistDTO dto,UriComponentsBuilder uriCB){
	    	logger.info("=====经销商微信预约专员修改信息=====");
	    	dealerBookInfoMaintenanceService.edit(id,dto);
	    	MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/dealerBookInfoMaintenance/edit").buildAndExpand().toUriString());
	        return new ResponseEntity<TmWxReserveSpecialistDTO>(headers, HttpStatus.CREATED);  	
	    }
		
	
	/**
	 * 经销商微信预约专员信息回显
	 */
	 @RequestMapping(value = "/getMaintenanceDealerById/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> getMaintenanceById(@PathVariable(value = "id") Long id){
	    	logger.info("=====经销商微信预约专员信息回显=====");
	    	TmWxReserveSpecialistPO ptPo=dealerBookInfoMaintenanceService.getMaintenanceDealerById(id);
	        return ptPo.toMap();
	    }
	
	
	/**
	 * 经销商微信预约专员删除
	 */
	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
	    	logger.info("=====经销商微信预约专员删除=====");
	    	dealerBookInfoMaintenanceService.delete(id);
	    }
	
}
