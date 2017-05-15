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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TtOrderSendTimeManagePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.orderSendTimeManageDTO.TtOrderSendTimeManageDTO;
import com.yonyou.dms.vehicle.domains.DTO.retailReportQuery.TtBigCustomerDirectDTO;
import com.yonyou.dms.vehicle.service.orderSendTimeManage.OrderSendTimeManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 订单发送时间维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/orderSendTimeManage")
public class OrderSendTimeManageController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	OrderSendTimeManageService   sendTimeManageService;
	
	/**
	 * 订单发送时间查询
	 * @param queryParam
	 * @return
	 */
	   @RequestMapping(value="/sendTimeManageSearch",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto querySendTime() {
	    	logger.info("==============订单发送时间查询=============");
	        PageInfoDto pageInfoDto =sendTimeManageService.orderSendTimeQuery();
	        return pageInfoDto;  
	    }
		/**
		 *通过id删除订单发送时间
		 */
	    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void deleteSendTime(@PathVariable("id") Long id, UriComponentsBuilder uriCB, TtBigCustomerDirectDTO ptdto) {
	    	logger.info("=====通过id删除订单发送时间=====");
	    	sendTimeManageService.deleteOrderSendTimeById(id);
	    }
		
		/**
		 * 通过id查询订单发送时间
		 */
	    @RequestMapping(value = "/getSendTime/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> getSendTimeById(@PathVariable(value = "id") Long id,TtOrderSendTimeManageDTO  dto){
	    	logger.info("=====通过id查询订单发送时间=====");
	    	TtOrderSendTimeManagePO ptPo=sendTimeManageService.getSendTimeById(id,dto);
	        return ptPo.toMap();
	    }
		
		/**
		 * 通过id修改订单发送时间
		 */
	    @RequestMapping(value = "/modify/{id}", method = RequestMethod.PUT)
	    @ResponseBody
	    public ResponseEntity<TtOrderSendTimeManageDTO> ModifyApplyData(@PathVariable(value = "id") Long id,@RequestBody TtOrderSendTimeManageDTO ptdto,UriComponentsBuilder uriCB){
	    	logger.info("=====通过id修改申请数据信息=====");
	    	sendTimeManageService.modifySendTime(id, ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        headers.set("Location", uriCB.path("/modify/{id}").buildAndExpand(id).toUriString());  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }
		
		/**
		 * 添加订单发送时间
		 */
	    @RequestMapping(value = "/addOrderSendTime",method = RequestMethod.POST)
	    public ResponseEntity<TtOrderSendTimeManageDTO> addApplyData(@RequestBody TtOrderSendTimeManageDTO ptdto,UriComponentsBuilder uriCB){
	        Long id =sendTimeManageService.addOrdersendTime(ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        headers.set("Location", uriCB.path("/orderSendTimeManage/addOrderSendTime").buildAndExpand(id).toUriString());  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }
	   
}
