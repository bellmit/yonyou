package com.yonyou.dms.vehicle.controller.orderSendTimeManage;

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
import com.yonyou.dms.vehicle.domains.DTO.k4OrderQueryManage.TmOrderCancelRemarksDTO;
import com.yonyou.dms.vehicle.service.orderSendTimeManage.CancelRemarkMaintenanceService;
import com.yonyou.f4.mvc.annotation.TxnConn;

@Controller
@TxnConn
@RequestMapping("/cancelRemark")
public class CancelRemarkMaintenanceController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	CancelRemarkMaintenanceService    cancelRemarkService;
	
	/**
	 * 取消备注维护查询
	 * @param queryParam
	 * @return
	 */
	   @RequestMapping(value="/cancelRemarkSearch",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryFreezeReason(@RequestParam Map<String, String> queryParam) {
	    	logger.info("============== 取消备注维护查询=============");
	        PageInfoDto pageInfoDto =cancelRemarkService.CancelRemarkQuery(queryParam);
	        return pageInfoDto;  
	    }
	   /**
	    * 新增取消备注维护
	    */
	    @RequestMapping(value = "/addCancelRemarks",method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseEntity<TmOrderCancelRemarksDTO> addApplyData(@RequestBody TmOrderCancelRemarksDTO ptdto,UriComponentsBuilder uriCB){
	    	logger.info("============== 新增取消备注维护=============");
	    	Long id =cancelRemarkService.addCancelRemark(ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        headers.set("Location", uriCB.path("/cancelRemark/addCancelRemarks").buildAndExpand(id).toUriString());  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }
	    
	    /**
	     * 新增时获得取消备注维护代码
	     */
	    @RequestMapping(value = "/getId",method = RequestMethod.GET)
	    @ResponseBody
	    public String getId(){
	    	logger.info("===== 新增时获得取消备注维护代码=====");
	    	return cancelRemarkService.getId();
	    }
	    
	    /**
	     * 修改数据回显
	     * @param id
	     * @return
	     */
	    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
	    @ResponseBody
	    public Map findDetailById(@PathVariable Long id){
	    	Map map = cancelRemarkService.findDetailById(id);
	    	return map;
	    }
	    
	    @RequestMapping(value = "/editCancelRemarks",method = RequestMethod.PUT)
	    @ResponseBody
	    public ResponseEntity<TmOrderCancelRemarksDTO> editApplyData(@RequestBody TmOrderCancelRemarksDTO ptdto,UriComponentsBuilder uriCB){
	    	logger.info("============== 新增取消备注维护=============");
	    	Long id =cancelRemarkService.editCancelRemark(ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        headers.set("Location", uriCB.path("/cancelRemark/editCancelRemarks").buildAndExpand(id).toUriString());  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }
	
	
}
