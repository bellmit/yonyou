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
import com.yonyou.dms.vehicle.domains.DTO.k4OrderQueryManage.TmOrderfreezereasonDTO;
import com.yonyou.dms.vehicle.service.orderSendTimeManage.OrderFreezeReasonService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 截停原因维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/freezeQuery")
public class OrderFreezeReasonController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	OrderFreezeReasonService  freezeReasonService ;
	
	/**
	 * 截停原因查询
	 */
	@RequestMapping(value="/freezeReasonSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryFreezeReason(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============截停原因查询=============");
        PageInfoDto pageInfoDto =freezeReasonService.FreezeReasonQuery(queryParam);
        return pageInfoDto;  
    }
	   
    
    /**
     * 新增时获取截停原因代码
     * @return
     */
    @RequestMapping(value = "/getAddOrderReasonId", method = RequestMethod.GET)
    @ResponseBody
    public String getAddOrderReasonId(){
    	logger.info("===== 新增时获取截停原因代码=====");
		return freezeReasonService.getAddOrderReasonId();
    }
    
    /**
     * 新增截停原因
     * @param dto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TmOrderfreezereasonDTO> add(@RequestBody TmOrderfreezereasonDTO dto,UriComponentsBuilder uriCB){
    	logger.info("===== 新增截停原因=====");
    	freezeReasonService.add(dto);
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/freezeQuery/add").buildAndExpand().toUriString());
        return new ResponseEntity<TmOrderfreezereasonDTO>(headers, HttpStatus.CREATED);  	
    }
  
    /**
     * 修改截停原因回显
     * @param freezeId
     * @return
     */
    @RequestMapping(value = "/getFreezeReason/{freezeId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getFreezeReason(@PathVariable Long freezeId){
    	logger.info("===== 修改截停原因回显=====");
		return freezeReasonService.findById(freezeId); 	
    }
    
    /**
     * 修改截停原因
     * @param dto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TmOrderfreezereasonDTO> edit(@RequestBody TmOrderfreezereasonDTO dto,UriComponentsBuilder uriCB){
    	logger.info("===== 修改截停原因=====");
    	freezeReasonService.edit(dto);
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/freezeQuery/edit").buildAndExpand().toUriString());
        return new ResponseEntity<TmOrderfreezereasonDTO>(headers, HttpStatus.CREATED);  	
    }
    
}
