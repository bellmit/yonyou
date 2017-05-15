package com.yonyou.dms.vehicle.controller.afterSales.preAuthorization;

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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovalDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrForeapprovalPO;
import com.yonyou.dms.vehicle.service.afterSales.preAuthorization.PreclaimAuditService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 索赔预授权审核作业 
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/preclaimAudit")
public class PreclaimAuditController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	PreclaimAuditService  preclaimAuditService;
	
	/**
	 * 索赔预授权审核作业查询
	 */
	@RequestMapping(value="/preclaimAuditSearch",method = RequestMethod.GET)
    @ResponseBody
   public PageInfoDto perClaimFeedSearch(@RequestParam Map<String, String> queryParam) {
   	logger.info("============== 索赔预授权审核作业查询=============");
       PageInfoDto pageInfoDto =preclaimAuditService.PreclaimAuditSearch(queryParam);
       return pageInfoDto;  
   }
	/**
	 * 同意，审核通过
	 */
	 @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	    @ResponseBody
	    public ResponseEntity<TtWrForeapprovalDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TtWrForeapprovalDTO dto,UriComponentsBuilder uriCB){
	    	logger.info("===== 同意，审核通过=====");
	    	preclaimAuditService.editTongyi(id, dto);
	    	MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/preclaimAudit/edit").buildAndExpand().toUriString());
	        return new ResponseEntity<TtWrForeapprovalDTO>(headers, HttpStatus.CREATED);  	
	    }
	 /**
	  * 通过id查询，信息回显
	  */
	  @RequestMapping(value = "/getForeapprovalById/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> getForeapprovalById(@PathVariable(value = "id") Long id){
	    	logger.info("=====通过id查询，信息回显=====");
	    	TtWrForeapprovalPO ptPo=preclaimAuditService.getForeapprovalById(id);
	        return ptPo.toMap();
	    }
	  /**
	   * 拒绝/退回
	   */
	  @RequestMapping(value = "/editJuTui/{id}/{type}", method = RequestMethod.PUT)
	    @ResponseBody
	    public ResponseEntity<TtWrForeapprovalDTO> editJuTui(@PathVariable(value = "id") Long id,@PathVariable(value = "type") String type,@RequestBody TtWrForeapprovalDTO dto,UriComponentsBuilder uriCB){
	    	logger.info("===== 拒绝/退回=====");
	    	preclaimAuditService.editJuTui(id, dto, type);
	    	MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/preclaimAudit/editJuTui").buildAndExpand().toUriString());
	        return new ResponseEntity<TtWrForeapprovalDTO>(headers, HttpStatus.CREATED);  	
	    }

}
